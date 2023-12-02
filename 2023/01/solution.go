package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"regexp"
	"strconv"
)

func main() {
	solution1()
	solution2()
}

func solution1() {
	file, err := os.Open("input.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	var ans int = 0
	for scanner.Scan() {
		currentLine := scanner.Text()
		var currentNum string = ""
		for _, char := range currentLine {
			if char >= '0' && char <= '9' {
				currentNum = string(char)
				break
			}
		}
		for index := len(currentLine) - 1; index >= 0; index-- {
			if currentLine[index] >= '0' && currentLine[index] <= '9' {
				currentNum += string(currentLine[index])
				break
			}
		}
		currentAns, err := strconv.Atoi(currentNum)
		if err == nil {
			ans += currentAns
		}
	}

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
	fmt.Println(ans)
}

type strNum struct {
	number string
	index  int
}

func numConv(n string) string {
	switch n {
	case "zero":
		return "0"
	case "one":
		return "1"
	case "two":
		return "2"
	case "three":
		return "3"
	case "four":
		return "4"
	case "five":
		return "5"
	case "six":
		return "6"
	case "seven":
		return "7"
	case "eight":
		return "8"
	case "nine":
		return "9"
	}
	return ""
}

func strRev(s string) string {
	var reversed string = ""
	for i := len(s) - 1; i >= 0; i-- {
		reversed += string(s[i])
	}
	return reversed
}

func matchNum(forward *regexp.Regexp, reverse *regexp.Regexp, l string) []strNum {
	initStr := forward.FindString(l)
	initIndex := forward.FindStringIndex(l)

	if len(initStr) == 0 {
		return []strNum{}
	}

	revStr := strRev(l)
	finalStr := reverse.FindString(revStr)
	finalIndex := len(l) - reverse.FindStringIndex(revStr)[1] - 1

	firstStrNum := strNum{number: numConv(initStr), index: initIndex[0]}
	lastStrNum := strNum{number: numConv(strRev(finalStr)), index: finalIndex}

	return []strNum{firstStrNum, lastStrNum}
}

func solution2() {
	forward := regexp.MustCompile("(zero|one|two|three|four|five|six|seven|eight|nine)")
	rev := strRev("zero|one|two|three|four|five|six|seven|eight|nine")
	reverse := regexp.MustCompile(fmt.Sprintf("(%s)", rev))
	file, err := os.Open("input.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)

	ans := 0

	for scanner.Scan() {
		currentLine := scanner.Text()
		var currentNum string = ""
		strNums := matchNum(forward, reverse, currentLine)
		hasStrNums := len(strNums) > 0

		firstStrNum := strNum{}
		secondStrNum := strNum{}

		if hasStrNums {
			firstStrNum = strNums[0]
			secondStrNum = strNums[1]
		}

		for index, char := range currentLine {
			if hasStrNums && firstStrNum.index <= index {
				currentNum = firstStrNum.number
				break
			}
			if char >= '0' && char <= '9' {
				currentNum = string(char)
				break
			}
		}
		for index := len(currentLine) - 1; index >= 0; index-- {
			if hasStrNums && secondStrNum.index >= index {
				currentNum += secondStrNum.number
				break
			}
			if currentLine[index] >= '0' && currentLine[index] <= '9' {
				currentNum += string(currentLine[index])
				break
			}
		}
		currentAns, err := strconv.Atoi(currentNum)
		if err == nil {
			ans += currentAns
		}
	}

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
	fmt.Println(ans)
}
