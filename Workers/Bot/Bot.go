package Bot

import (
	"bytes"
	"crypto"
	"crypto/rand"
	"crypto/rsa"
	"crypto/sha256"
	"crypto/x509"
	"encoding/base64"
	"encoding/hex"
	"encoding/json"
	"encoding/pem"
	"fmt"
	"io"
	"io/ioutil"
	"net/http"
	"strconv"
	"worker/Utils"
)

var BotAddress []string

type Bot struct {
	username   string
	password   string
	publicKey  string
	privateKey string
}

func (b *Bot) Test() {
	println("Hello, I'm a worker!")
}

func (b *Bot) Register() {

	privateKey, err := rsa.GenerateKey(rand.Reader, 1024)
	if err != nil {
		panic(err)
	}
	publicKey := &privateKey.PublicKey

	privatePem := pem.EncodeToMemory(
		&pem.Block{
			Type:  "RSA PRIVATE KEY",
			Bytes: x509.MarshalPKCS1PrivateKey(privateKey),
		},
	)

	publicKeyBytes, _ := x509.MarshalPKIXPublicKey(publicKey)

	publicPem := pem.EncodeToMemory(
		&pem.Block{
			Type:  "PUBLIC KEY",
			Bytes: publicKeyBytes,
		},
	)

	b.publicKey = string(publicPem)
	b.privateKey = string(privatePem)
	BotAddress = append(BotAddress, string(publicPem))
}

func (b *Bot) Mine() {
	params := map[string]string{
		"address": b.privateKey,
	}
	jsonValue, _ := json.Marshal(params)
	resp, err := http.Post("http://localhost:5000/mine", "application/json", bytes.NewBuffer(jsonValue))
	if err != nil {
		fmt.Print(err.Error())
		return
	}
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		println(err.Error())
		return
	}
	resp.Body.Close()
	var responseParams map[string]interface{}
	json.Unmarshal(body, &responseParams)

	status, _ := responseParams["status"].(string)
	if status == "failed" {
		//handle failure
	}

	// print(status)
	// println(bodyString)
}

func (b *Bot) getBalance() int {
	params := map[string]string{
		"address": b.publicKey,
	}
	jsonValue, _ := json.Marshal(params)
	resp, err := http.Post("http://localhost:5000/balance", "application/json", bytes.NewBuffer(jsonValue))
	if err != nil {
		println(err.Error())
		return 0
	}

	if resp.StatusCode == 200 {
		body, _ := io.ReadAll(resp.Body)
		// amount, _ := strconv.ParseInt(string(body), 10, 64)
		amount, _ := strconv.Atoi(string(body))
		// println(amount)
		return amount
	}
	resp.Body.Close()
	return 0
}

func (b *Bot) SendRandom() {
	toAddress := BotAddress[Utils.RandIntInRange(0, len(BotAddress))]
	fromAddress := b.publicKey
	balance := b.getBalance()
	amount := Utils.RandIntInRange(0, balance)

	// println(fromAddress)
	// println(toAddress)
	// println(balance)

	msg := []byte(fromAddress + toAddress + strconv.Itoa(amount))

	msgHash := sha256.New()
	_, err := msgHash.Write(msg)
	if err != nil {
		panic(err)
	}
	msgHashSum := msgHash.Sum(nil)
	fmt.Printf("msgHash:\t %x \n", msgHashSum)

	block, _ := pem.Decode([]byte(b.privateKey))
	if block == nil {
		panic("Can not decode pem data")
	}

	privateKey, err := x509.ParsePKCS1PrivateKey(block.Bytes)

	if err != nil {
		panic(err.Error())
	}

	signature, err := rsa.SignPSS(rand.Reader, privateKey, crypto.SHA256, msgHashSum, nil)
	if err != nil {
		panic(err)
	}
	// println(string(signature))

	requestParams := map[string]interface{}{
		"transaction": map[string]string{
			"from":   fromAddress,
			"to":     toAddress,
			"amount": strconv.Itoa(amount),
		},
		"signature": base64.StdEncoding.EncodeToString(signature),
	}

	fmt.Print("Hex: ", hex.EncodeToString(signature))

	jsonValue, _ := json.Marshal(requestParams)
	resp, err := http.Post("http://localhost:5000/transaction", "application/json", bytes.NewBuffer(jsonValue))

	if err != nil {
		println(err.Error())
		return
	}

	body, err := io.ReadAll(resp.Body)
	if err != nil {
		println(err.Error())
		return
	}
	bodyString := string(body)
	println(bodyString)
}
func (b *Bot) Run() {
	b.Register()
	b.Mine()
	b.getBalance()
	b.SendRandom()
}
