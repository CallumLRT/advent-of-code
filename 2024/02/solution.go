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

 func reportCheck(report string) (bool, int) {
    safe := true
    failedIndex := -1
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
            failedIndex = index // The index of the previous level in `levels` due to `range levels[1:]`
            break
        }
    }
    
    return safe, failedIndex
 }

func Solution1(reports []string) int {
    safe_reports := 0

    for _, report := range reports {
        if safe, _ := reportCheck(report); safe {
            safe_reports++
        }
    }
    return safe_reports
}

func deleteElement(slice []string, index int) []string {
    return append(slice[:index], slice[index+1:]...)
}

func testNeighbours(report string, failedIndex int) bool {
    levels := strings.Split(report, " ")
    var testIndexes []int
    if failedIndex > 0 {
        testIndexes = []int{failedIndex-1, failedIndex+1} // This is safe because we're basing this index off an offset of 1, i.e. `range levels[1:]`
    } else {
        testIndexes = []int{0, 1}
    }

    for _, index := range testIndexes {
        tmp := make([]string, len(levels))
        copy(tmp, levels)
        tmp = deleteElement(tmp, index)

        if safe, _ := reportCheck(strings.Join(tmp, " ")); safe {
            return true
        }
    }
    return false
}

func Solution2(reports []string) int {
    safe_reports := 0

    for _, report := range reports {
        if safe, failedIndex := reportCheck(report); safe {
            safe_reports++
        } else {
            if testNeighbours(report, failedIndex) {
                safe_reports++
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
