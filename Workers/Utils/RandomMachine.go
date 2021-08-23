package Utils

import (
	"math/rand"
	"time"
)

func GenerateRandomString(n int) string {
	var letters = []rune("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789")
	rand.Seed(time.Now().UTC().UnixNano())
	s := make([]rune, n)
	for i := range s {
		s[i] = letters[rand.Intn(len(letters))]
	}
	return string(s)
}

func RandFloatInRange(a float64, b float64) float64 {
	rand.Seed(time.Now().UTC().UnixNano())
	return a + rand.Float64()*(b-a)
}

func RandIntInRange(a int, b int) int {
	rand.Seed(time.Now().UTC().UnixNano())
	return rand.Intn(b-a) + a
}
