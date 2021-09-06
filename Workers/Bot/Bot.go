package Bot

import (
	"bytes"
	"crypto"
	"crypto/rand"
	"crypto/rsa"
	"crypto/sha256"
	"crypto/x509"
	"encoding/base64"
	"encoding/json"
	"encoding/pem"
	"fmt"
	"io"
	"io/ioutil"
	"net/http"
	"strconv"
	"time"
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
	b.username = Utils.GenerateRandomString(10)

	params := map[string]string{
		"username":  b.username,
		"publicKey": b.publicKey,
	}
	jsonValue, _ := json.Marshal(params)
	resp, err := http.Post("http://server:5000/register", "application/json", bytes.NewBuffer(jsonValue))
	if err != nil {
		println(err.Error())
		return
	} else {
		body, _ := ioutil.ReadAll(resp.Body)
		resp.Body.Close()
		println(string(body))
		BotAddress = append(BotAddress, b.username)
	}
}

func (b *Bot) Mine() {
	params := map[string]string{
		"address": b.privateKey,
	}
	jsonValue, _ := json.Marshal(params)
	resp, err := http.Post("http://server:5000/mine", "application/json", bytes.NewBuffer(jsonValue))
	if err != nil {
		fmt.Print(err.Error())
		return
	}
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		println(err.Error())
		return
	} else {
		resp.Body.Close()
		var responseParams map[string]interface{}
		println(string(body))
		json.Unmarshal(body, &responseParams)

		status, _ := responseParams["status"].(string)
		if status == "failed" {
			//handle failure
		}
	}

	// print(status)
	// println(bodyString)
}

func (b *Bot) getBalance() int {
	resp, err := http.Post("http://server:5000/balance/"+b.username, "application/json", nil)
	if err != nil {
		println(err.Error())
		return 0
	}

	if resp.StatusCode == 200 {
		body, _ := io.ReadAll(resp.Body)
		// amount, _ := strconv.ParseInt(string(body), 10, 64)
		var jsonBody map[string]interface{}
		err := json.Unmarshal(body, &jsonBody)
		if err != nil {
			panic(err)
		}

		// println(string(body))
		amount := int(jsonBody["balance"].(float64))
		resp.Body.Close()
		// println(amount)
		return amount
	}

	return 0
}

func (b *Bot) SendRandom() {
	if len(BotAddress) == 0 {
		return
	}
	toAddress := BotAddress[Utils.RandIntInRange(0, len(BotAddress))]
	fromAddress := b.username
	balance := b.getBalance()
	if balance == 0 {
		return
	}
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
	// fmt.Printf("msgHash:\t %x \n", msgHashSum)
	// println(b.privateKey)
	block, _ := pem.Decode([]byte(b.privateKey))
	privateKey, err := x509.ParsePKCS1PrivateKey(block.Bytes)
	//

	if err != nil {
		panic(err.Error())
	}

	signature, err := rsa.SignPSS(rand.Reader, privateKey, crypto.SHA256, msgHashSum, nil)
	if err != nil {
		panic(err)
	}
	// println(string(signature))

	requestParams := map[string]interface{}{
		"from":      fromAddress,
		"to":        toAddress,
		"amount":    strconv.Itoa(amount),
		"signature": base64.StdEncoding.EncodeToString(signature),
		"isbot":     true,
	}

	// fmt.Print("Hex: ", hex.EncodeToString(signature))

	jsonValue, _ := json.Marshal(requestParams)
	println(string(jsonValue))
	resp, err := http.Post("http://server:5000/transaction", "application/json", bytes.NewBuffer(jsonValue))

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

func (b *Bot) getRandomCampaign() string {
	resp, err := http.Get("http://server:5000/campaigns")
	if err != nil {
		panic(err.Error())
	}

	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		panic(err.Error())
	}

	type campaign_t struct {
		CampaignName     string
		CampaignOwnerKey string
		ExpireDate       string
		Description      string
		TargetAmount     interface{}
		Propaganda       string
		TotalAmount      interface{}
	}

	var campaigns []campaign_t

	err = json.Unmarshal(body, &campaigns)
	if err != nil {
		panic(err.Error())
	}
	resp.Body.Close()

	// println(string(body))
	// fmt.Printf("Campaign : %+v", campaigns[0])
	// println(campaigns[0].CampaignName)
	if len(campaigns) == 0 {
		return ""
	}
	return campaigns[Utils.RandIntInRange(0, len(campaigns))].CampaignName
}

func (b *Bot) Donate() {
	targetCampaign := b.getRandomCampaign()
	if targetCampaign == "" {
		print("No campaign found")
		return
	}

	toAddress := targetCampaign
	fromAddress := b.username
	balance := b.getBalance()
	if balance == 0 {
		return
	}
	amount := Utils.RandIntInRange(0, balance)
	if amount == 0 {
		amount = balance
	}
	msg := []byte(fromAddress + toAddress + strconv.Itoa(amount))

	msgHash := sha256.New()
	_, err := msgHash.Write(msg)
	if err != nil {
		panic(err)
	}
	msgHashSum := msgHash.Sum(nil)
	block, _ := pem.Decode([]byte(b.privateKey))
	privateKey, err := x509.ParsePKCS1PrivateKey(block.Bytes)

	if err != nil {
		panic(err.Error())
	}

	signature, err := rsa.SignPSS(rand.Reader, privateKey, crypto.SHA256, msgHashSum, nil)
	if err != nil {
		panic(err)
	}

	requestParams := map[string]interface{}{
		"from":         fromAddress,
		"campaignName": toAddress,
		"amount":       amount,
		"signature":    base64.StdEncoding.EncodeToString(signature),
		"isbot":        true,
	}

	// fmt.Print("Hex: ", hex.EncodeToString(signature))

	jsonValue, _ := json.Marshal(requestParams)
	println(string(jsonValue))
	resp, err := http.Post("http://server:5000/donate", "application/json", bytes.NewBuffer(jsonValue))

	if err != nil {
		panic(err.Error())
	}

	_, err = io.ReadAll(resp.Body)
	if err != nil {
		panic(err.Error())
	}
	// bodyString := string(body)

	println("Bot " + b.username + " donated " + strconv.Itoa(amount) + " to " + targetCampaign)
}
func (b *Bot) Run() {
	b.Register()
	// b.getBalance()
	// for {
	// b.Mine()
	// 	b.SendRandom()
	// 	time.Sleep(5 * time.Second)
	// }
	for {
		if b.getBalance() == 0 {
			break
		}
		b.Donate()
		b.Mine()

		time.Sleep(time.Duration(Utils.RandIntInRange(0, 10)) * time.Second)
	}

	// println(b.getRandomCampaign())
}
