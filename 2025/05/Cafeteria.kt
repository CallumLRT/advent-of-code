import java.io.File

fun main() {
    val cafeteria = Cafeteria(File("/workspaces/advent-of-code/2025/05/input.txt"))

    println("Solution 1 result" + " = " + cafeteria.solutionOne())
    println("Solution 2 result" + " = " + cafeteria.solutionTwo())
}

class Cafeteria(private val file: File) {
    val ingredients: MutableList<Long> = mutableListOf()
    val freshRanges: MutableSet<LongRange> = mutableSetOf()
    
    init {
        println("Cafeteria initialized with file: ${file.path}")
        loadData()
    }

    public fun solutionTwo(): Long {
        var sortedRanges = freshRanges.sortedBy { it.first }.toMutableList()
        
        var index = 0

        while (index <= sortedRanges.size - 2) {
            val currentRange = sortedRanges[index]
            val nextRange = sortedRanges.elementAt(index + 1)

            if(currentRange.last >= nextRange.first) {
                val newStart = currentRange.first
                val newEnd = maxOf(currentRange.last, nextRange.last)
                sortedRanges[index] = newStart..newEnd
                sortedRanges.remove(nextRange)
            } else {
                index++
            }
        }

        var totalIds = 0L

        for (range in sortedRanges) {
            totalIds += range.last - range.first + 1
        }

        return totalIds
    }

    public fun solutionOne(): Int {
        var freshIngredientCount = 0
        for (ingredient in ingredients) {
            for (range in freshRanges) {
                if (range.contains(ingredient)) {
                    freshIngredientCount++
                    break
                }
            }
        }
        
        return freshIngredientCount
    }

    private fun loadData() {
        file.reader().useLines { lines ->
            var isIngredientSection = false
            for (line in lines) {
                if (line.isBlank()) {
                    isIngredientSection = true
                    continue
                }
                if (isIngredientSection) {
                    ingredients.add(line.trim().toLong())
                } else {
                    val parts = line.split("-")
                    val start = parts.get(0).toLong()
                    val end = parts.get(1).toLong()
                    freshRanges.add(start..end)
                }
            }
        }
    }
}