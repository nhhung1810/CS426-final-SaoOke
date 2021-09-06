package main

import (
	"container/list"
	"time"
	"worker/Bot"
)

func main() {
	// args := os.Args[1:]
	// fmt.Print(args)
	var numBot int64
	// if args != nil {
	// 	numBot, _ = strconv.ParseInt(args[0], 10, 64)
	// } else {
	// 	numBot = 5
	// }

	numBot = 5

	for {
		queue := list.New()
		var i int64
		for i = 0; i < numBot; i++ {
			var bot = new(Bot.Bot)
			queue.PushBack(bot)
			go bot.Run()
		}
		time.Sleep(30 * time.Second)
	}

	// var s = new(Sign.Sign)
	// s.Test()

	// args := os.Args[1:]
	// fmt.Print(args)

}
