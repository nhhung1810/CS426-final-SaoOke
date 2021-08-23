package Bot

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
	"net/http"
	"strconv"

	"../Utils"
)

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
	b.username = Utils.GenerateRandomString(10)
	b.password = Utils.GenerateRandomString(10)

	params := map[string]string{
		"username": b.username,
		"password": b.password,
	}
	jsonValue, _ := json.Marshal(params)
	resp, err := http.Post("http://localhost:5000/register", "application/json", bytes.NewBuffer(jsonValue))
	if err != nil {
		fmt.Print(err.Error())
		return
	}
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		fmt.Print(err.Error())
		return
	}
	resp.Body.Close()

	bodyString := string(body)
	var responseParams map[string]interface{}

	// println(bodyString)
	json.Unmarshal([]byte(bodyString), &responseParams)

	errorMessage, _ := responseParams["error"].(string)

	if len(errorMessage) != 0 {
		println(errorMessage)
		return
	}
	b.publicKey, _ = responseParams["publicKey"].(string)
	b.privateKey, _ = responseParams["privateKey"].(string)
	// print(b.publicKey, b.privateKey)
}

func (b *Bot) Mine() {
	params := map[string]string{
		"address": b.publicKey,
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

func (b *Bot) getBalance() float64 {
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
		amount, _ := strconv.ParseFloat(string(body), 32)
		// println(amount)
		return amount
	}
	resp.Body.Close()
	return 0
}
func (b *Bot) Run() {
	b.Register()
	b.Mine()
	b.getBalance()
}