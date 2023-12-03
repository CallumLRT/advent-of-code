// groovy .\Solution.groovy

// ==== Solution 1 ====

List readAndMark(File file) {
    def partsMatrix = []
    def line, lineNumber = 0;
    file.withReader { reader ->
        while ((line = reader.readLine()) != null) {
            partsMatrix.add([])
            line.eachWithIndex { character, index ->
                strToChar = character as char
                if (strToChar.isDigit() || strToChar == '.') {
                    partsMatrix[lineNumber].add(false)
                } else {
                    partsMatrix[lineNumber].add(true)
                }
            }
            lineNumber++
        }
    }
    return partsMatrix
}

def validateNumber(List partsMatrix, int currentNumberStartIndex, int endOfNumber, int lineNumber) {
    List directions = [[-1,-1],[0,-1],[1,-1],[-1,0],[1,0],[-1,1],[0,1],[1,1]]
    for (int index in currentNumberStartIndex..endOfNumber) {
        for (List direction in directions) {
            if (partsMatrix?[lineNumber+direction[0]]?[index+direction[1]]) {
                return true
            }
        }
    }
    return false    
}

def readAndFindValid(File file, List partsMatrix) {
    int result = 0
    def line, lineNumber = 0;
    file.withReader { reader ->
        while ((line = reader.readLine()) != null) {
            int index
            String currentNumber = ""
            int currentNumberStartIndex = -1

            for (index = 0; index < line.length(); index++) {
                character = line[index] as char
                if (character.isDigit() && currentNumber == "") {
                    currentNumber += character
                    currentNumberStartIndex = index
                    continue
                }

                if (character.isDigit()) {
                    currentNumber += character
                    continue
                }
                if (currentNumber != "") {
                    if (validateNumber(partsMatrix, currentNumberStartIndex, index - 1, lineNumber)) {
                        result += currentNumber as Integer
                    }
                    currentNumber = ""
                }
            }
            // Don't forget line end case!!
            if (currentNumber != "") {
                if (validateNumber(partsMatrix, currentNumberStartIndex, index, lineNumber)) {
                    result += currentNumber as Integer
                }
            }
            lineNumber++
        }
    }
    return result
}

def Solution1() {
    File file = new File("input.txt")
    List partsMatrix = readAndMark(file)
    println(readAndFindValid(file, partsMatrix))
}

// ==== Solution 2 ====

List readAndMarkGears(File file) {
    def gearsMatrix = []
    def line, lineNumber = 0;
    file.withReader { reader ->
        while ((line = reader.readLine()) != null) {
            gearsMatrix.add([])
            line.eachWithIndex { character, index ->
                strToChar = character as char
                if (strToChar == '*') {
                    gearsMatrix[lineNumber].add(true)
                } else {
                    gearsMatrix[lineNumber].add(false)
                }
            }
            lineNumber++
        }
    }
    return gearsMatrix
}

List readAndMarkDigits(File file) {
    def digitsMatrix = []
    def line, lineNumber = 0;
    file.withReader { reader ->
        while ((line = reader.readLine()) != null) {
            digitsMatrix.add([])
            line.eachWithIndex { character, index ->
                strToChar = character as char
                if (strToChar.isDigit()) {
                    digitsMatrix[lineNumber].add(strToChar)
                } else {
                    digitsMatrix[lineNumber].add(null)
                }
            }
            lineNumber++
        }
    }
    return digitsMatrix
}

int findFullNumber(List currentLine, int startIndex) {
    // Finding the starting index of this number
    currentIndex = startIndex
    currentItem = null

    do {
        currentIndex -= 1
        currentItem = currentLine?[currentIndex]
    } while (currentItem != null)

    // Now, starting at where we know the number begins
    currentIndex++
    currentItem = currentLine[currentIndex]
    def result = []
    while (currentItem != null) {
        result.add(currentItem)
        currentIndex++
        currentItem = currentLine?[currentIndex]
    }

    return result.size() == 0 ? 0 : result.join() as int
}

List lineByLineGearValidation(List currentLine, int gearIndex) {
    // `validateIndexes` should be the locations on the given line where numbers can be (in-order)

    // single or dual numbers could be found, so we should look for those posibilities
    // i.e. ... 6 null 7 ... where 6 is the end (or entire) number1, and 7 is the start (or entire) number2
    int gearLIndex = gearIndex - 1
    int gearRIndex = gearIndex + 1

    if (currentLine?[gearLIndex] != null && currentLine?[gearIndex] == null && currentLine?[gearRIndex] != null) {
        // we have two numbers in this range, and we should return both of them
        return [findFullNumber(currentLine, gearLIndex), findFullNumber(currentLine, gearRIndex)]
    }
    
    def validateIndexes = [gearLIndex, gearIndex, gearRIndex]
    // we can't have more than one number in range
    for (int index in validateIndexes) {
        if (currentLine[index] != null) {
            return [findFullNumber(currentLine, index)]
        }
    }
    
    return [] // Catch-all if no numbers were found
}

int validateGear(List gearsMatrix, List digitsMatrix, int lineNumber, int index) {
    // If this is not a gear
    if (gearsMatrix[lineNumber][index] == false) {
        return 0
    }

    def numbersFound = []

    // If we have a gear, find out if we have exactly two adjacent numbers
    for (int lineAdj in -1..1) {
        numbersFound += lineByLineGearValidation(digitsMatrix[lineNumber+lineAdj], index)
        if (numbersFound.size() > 2) {
            return 0
        }
    }

    return numbersFound.size() == 2 ? numbersFound[0] * numbersFound[1] : 0
}

def readAndFindValidGear(List gearsMatrix, List digitsMatrix) {
    result = 0 
    for (int lineNumber = 0; lineNumber < gearsMatrix.size(); lineNumber++) {
        for (int index = 0; index < gearsMatrix[lineNumber].size(); index++) {
            result += validateGear(gearsMatrix, digitsMatrix, lineNumber, index)
        }
    }
    return result
}

def Solution2() {
    File file = new File("input.txt")
    List gearsMatrix = readAndMarkGears(file)
    List digitsMatrix = readAndMarkDigits(file)
    println(readAndFindValidGear(gearsMatrix, digitsMatrix))
}

// ==== Run all ====

Solution1()
Solution2()
