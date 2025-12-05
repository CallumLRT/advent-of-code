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

func leftTurn(position *int, password *int, clicks int, s int) {
	startingPosition := *position
	*position = *position - clicks

	if *position < 0 {
		*position = *position + 100
		if s == 2 && *position != 0 && startingPosition != 0 {
			*password += 1
		}
	}
}

func rightTurn(position *int, password *int, clicks int, s int) {
	startingPosition := *position
	*position = *position + clicks

	if *position > 99 {
		*position = *position - 100
		if s == 2 && *position != 0 && startingPosition != 0 {
			*password += 1
		}
	}
}

func actionInstruction(position *int, password *int, i string, s int) {
	clicks, err := strconv.Atoi(i[1:])
	check(err)

	if s == 2 {
		fullRotations := clicks / 100
		*password += fullRotations
	}

	clicks = clicks % 100

	if i[0] == 'L' {
		leftTurn(position, password, clicks, s)
	} else {
		rightTurn(position, password, clicks, s)
	}
}

func findSolution(path string, solution int) int {
	password := 0
	position := 50
	inputFile, err := os.Open(path)
	check(err)

	scanner := bufio.NewScanner(inputFile)
	line := 0
	for scanner.Scan() {
		instruction := scanner.Text()
		line++

		actionInstruction(&position, &password, instruction, solution)

		if position == 0 {
			password += 1
		}
	}

	check(scanner.Err())
	return password
}

func main() {
	fmt.Printf("Password: %d \n", findSolution("input.txt", 2))
}
