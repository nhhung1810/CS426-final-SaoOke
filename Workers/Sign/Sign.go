package Sign

import (
	"crypto"
	"crypto/rand"
	"crypto/rsa"
	"crypto/sha256"
	"crypto/x509"
	"encoding/base64"
	"encoding/pem"
	"fmt"
)

var BotAddress []string

type Sign struct {
	username   string
	password   string
	publicKey  string
	privateKey string
}

func (b *Sign) Test() {
	// println("Hello, I'm a Sign machine!")
	b.GenerateKey()
	b.sign()
}

func (b *Sign) GenerateKey() {
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
	println(b.publicKey)
	println(b.privateKey)
	BotAddress = append(BotAddress, string(publicPem))
}

func (b *Sign) sign() {
	msg := []byte("Hello")

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

	var parsedKey interface{}
	if parsedKey, err = x509.ParsePKCS8PrivateKey(block.Bytes); err != nil { // note this returns type `interface{}`
		println("Unable to parse RSA private key")
		panic(err.Error())
	}
	var privateKey *rsa.PrivateKey
	var ok bool
	privateKey, ok = parsedKey.(*rsa.PrivateKey)
	if !ok {
		println("Unable to parse RSA private key, generating a temp one")
		panic(err.Error())
	}

	// privateKey, err := x509.ParsePKCS1PrivateKey(block.Bytes)
	// if err != nil {
	// 	panic(err.Error())
	// }

	signature, err := rsa.SignPSS(rand.Reader, privateKey, crypto.SHA256, msgHashSum, nil)
	if err != nil {
		panic(err)
	}

	var tmp = base64.StdEncoding.EncodeToString(signature)
	println(tmp)
}
