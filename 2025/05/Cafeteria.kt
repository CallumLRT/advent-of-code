import java.io.File

fun main() {
    val cafeteria = Cafeteria(File("/workspaces/advent-of-code/2025/05/input.txt"))

    println("Solution 1 result:" + " = " + cafeteria.solutionOne())
}

class Cafeteria(private val file: File) {
    val ingredients: MutableList<Long> = mutableListOf()
    val freshRanges: MutableSet<LongRange> = mutableSetOf()
    
    init {
        println("Cafeteria initialized with file: ${file.path}")
        loadData()
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
                    val start = parts.getOrNull(0)?.toLong()
                    val end = parts.getOrNull(1)?.toLong()
                    if (start != null && end != null) {
                        freshRanges.add(start..end)
                    }
                }
            }
        }
    }
}