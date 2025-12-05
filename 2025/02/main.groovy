class Main {

    boolean checkInvalidIdSol1(Long currentId) {
        String currentIdStr = currentId as String
        int stringLength = currentIdStr.length()

        // An odd length can never be invalid
        if (stringLength % 2 != 0) {
            return false
        }

        int halfStringLength = stringLength / 2
        int i = halfStringLength
        for (int j = 0; j < halfStringLength; j++) {
            if (currentIdStr[i] != currentIdStr[j]) {
                return false
            }
            i++
        }

        return true
    }

    boolean checkRepeatedNums(String repeatedNum, String currentIdStr, int stringLength) {
        // The whole string needs to be repeated, so if it's indivisible, skip processing
        if (stringLength % repeatedNum.length() != 0) {
            return false
        }
        int j = 0
        for (int i = repeatedNum.length(); i < stringLength; i++) {
            if (currentIdStr[i] != repeatedNum[j]) {
                return false
            }

            j++
            if (j == repeatedNum.length()) {
                j = 0
            }
        }
        return true
    }

    boolean checkInvalidIdSol2(Long currentId) {
        String currentIdStr = currentId as String
        String repeatedNum = ''
        int stringLength = currentIdStr.length()

        for (int i = 0; i < stringLength && repeatedNum.length() < Math.ceil(stringLength / 2); i++) {
            repeatedNum += currentIdStr[i]
            if (checkRepeatedNums(repeatedNum, currentIdStr, stringLength)) {
                return true
            }
        }
        return false
    }

    def main() {
        String fileContents = new File('sample.txt').text
        Long invalidIdTotal = 0
        int solution = 2

        for (String idRange in fileContents.split(',')) {
            String[] idRangeArr = idRange.split('-')
            Long startId = idRangeArr[0] as Long
            Long endId = idRangeArr[1] as Long

            for (Long id in startId..endId) {
                if (solution == 1 && checkInvalidIdSol1(id)) {
                    invalidIdTotal += id
                } else if (checkInvalidIdSol2(id)) {
                    invalidIdTotal += id
                }
            }
        }

        println('Invalid ID Total:')
        println(invalidIdTotal)
    }

}
