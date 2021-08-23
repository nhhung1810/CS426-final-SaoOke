package Bot

import (
	"fmt"
	"net/http"
	"net/url"

	"../Utils"
)

type Bot struct {
	username string
	password string
	key      string
}

func (b *Bot) Test() {
	b.Register()
	println(b.username)
	println(b.password)
}

func (b *Bot) Register() {
	b.username = Utils.GenerateRandomString(10)
	b.password = Utils.GenerateRandomString(10)

	resp, err := http.PostForm("", url.Values{"username": {b.username}, "password": {b.password}})
	if err != nil {
	}
	fmt.Print(resp)

}
func (b *Bot) Run() {
	b.Register()
}
