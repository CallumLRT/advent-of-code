package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
)

type Direction int

const (
initial Direction = iota 
increasing
decreasing
)

func diff(a, b int) int {
    if a < b {
       return b - a
    }
    return a - b
 }

 func (d *Direction) safeSequence(current, previous int) bool {
	min_delta := 1
	max_delta := 3

    if current == previous {
        return false
    }

    if current > previous {
        if *d == decreasing {
            return false
        }
        *d = increasing
    }

    if current < previous {
        if *d == increasing {
            return false
        }
        *d = decreasing
    }

    difference := diff(current, previous)

    if difference > max_delta || difference < min_delta {
        return false
    }

    return true
 }

 func reportCheck(report string) bool {
    safe := true
    direction := initial
    levels := strings.Split(report, " ")

    for index, level := range levels[1:] {
        currentLevel, err := strconv.Atoi(level)
        if err != nil {
            panic(err)
        }
        previousLevel, err := strconv.Atoi(levels[index])
        if err != nil {
            panic(err)
        }

        safe = direction.safeSequence(currentLevel, previousLevel)

        if !safe {
            break
        }
    }
    
    return safe
 }

func Solution1(reports []string) int {
    safe_reports := 0

    for _, report := range reports {
        if reportCheck(report) {
            safe_reports++
        }
    }
    return safe_reports
}

func deleteElement(slice []string, index int) []string {
    return append(slice[:index], slice[index+1:]...)
}

func Solution2(reports []string) int {
    safe_reports := 0

    for _, report := range reports {
        if reportCheck(report) {
            safe_reports++
        } else {
            levels := strings.Split(report, " ")
            for index := range levels {
                tmp := make([]string, len(levels))
                copy(tmp, levels)
                tmp = deleteElement(tmp, index)

                if reportCheck(strings.Join(tmp, " ")) {
                    safe_reports++
                    break
                }
            }
        }
    }
    return safe_reports
}

func main() {
    file, err := os.Open("input.txt")
    if err != nil {
        log.Fatal(err)
    }
    defer file.Close()

    var reports []string

    scanner := bufio.NewScanner(file)
    for scanner.Scan() {
        reports = append(reports, scanner.Text())
    }

    if err := scanner.Err(); err != nil {
        log.Fatal(err)
    }

    fmt.Println(Solution1(reports))
    fmt.Println(Solution2(reports))
}
