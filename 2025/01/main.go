package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func check(e error) {
	if e != nil {
		panic(e)
	}
}

func leftTurn(p *int, c int) {
    *p = *p - c
    
    if *p < 0 {
        *p = *p + 100
    }
}

func rightTurn(p *int, c int) {
    *p = *p + c
    
    if *p > 99 {
        *p = *p - 100
    }
}

func actionInstruction(p *int, i string) {
    clicks, err := strconv.Atoi(i[1:])
    check(err)

    clicks = clicks % 100

    if i[0] == 'L' {
        leftTurn(p, clicks)
    } else {
        rightTurn(p, clicks)
    }
}

func main() {
    password := 0
    position := 50
    inputFile, err := os.Open("./input.txt")
    check(err)
    
    scanner := bufio.NewScanner(inputFile)
    line := 0
    for scanner.Scan() {
        instruction := scanner.Text()
        fmt.Printf("Line: %d | Instruction: %q | Position: %d \n", line, instruction, position)
        line++

        actionInstruction(&position, instruction)

        if (position == 0) {
            password += 1
        }
        
        fmt.Printf("Position after: %d | Password count: %d \n", position, password)
    }
    
    fmt.Printf("Password: %d \n", password)
    check(scanner.Err())
}