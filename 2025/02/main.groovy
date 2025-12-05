class Main {

    boolean checkInvalidId(Long currentId) {
        String currentIdStr = currentId as String
        int stringLength = currentIdStr.length()

        // Solution 1: An odd length can never be invalid
        if (stringLength % 2 != 0) {
            return false
        }

        int halfStringLength = stringLength / 2
        int i = halfStringLength
        for (int j = 0; j < halfStringLength; j++) {
            if (currentIdStr[i] != currentIdStr[j]) {
                return false
            }
            i += 1
        }

        return true
    }

    def main() {
        String fileContents = new File('input.txt').text
        Long invalidIdTotal = 0

        for (String idRange in fileContents.split(',')) {
            String[] idRangeArr = idRange.split('-')
            Long startId = idRangeArr[0] as Long
            Long endId = idRangeArr[1] as Long

            for (Long id in startId..endId) {
                if (checkInvalidId(id)) {
                    invalidIdTotal += id
                }
            }
        }

        println('Invalid ID Total:')
        println(invalidIdTotal)
    }

}
