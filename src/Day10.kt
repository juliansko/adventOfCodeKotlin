import kotlin.math.pow

fun main() {
    val directions = listOf(
        Pair(0,1),
        Pair(0,-1),
        Pair(-1,0),
        Pair(1,0),
    )

    fun findnext(map: List<IntArray>, position: Pair<Int, Int>, height: Int = 0): List<Pair<Int, Int>> {
        var localScore: MutableList<Pair<Int, Int>> = mutableListOf()
        for (direction in directions) {
            val nextSpot = position + direction
            if (!map.check(nextSpot)) continue
            if (map[nextSpot] == height + 1) localScore += findnext(map, nextSpot, height + 1)
            if (map[nextSpot] == 9 && height == 8) {
                localScore.add(nextSpot)
            }
        }
        return localScore
    }
    fun part1(input: List<String>, allMode: Boolean = false): Int {
        val mountain =input.map { it.map { it.digitToInt() }.toIntArray() }
        var sum = 0
        var allSum = 0
        for ((index, line) in mountain.withIndex()) {
            for ((indexH, col) in line.withIndex()) {
                if (col == 0) {
                    val goals = findnext(mountain, Pair(index, indexH))
                    allSum += goals.size
                    sum += goals.distinct().size
                }
            }
        }
        if(allMode) return allSum
        return  sum

        }

    fun part2(input: List<String>): Int {
       return input.size
    }

    // Or read a large test input from the `input/Day01_test.txt` file:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)
    check(part1(testInput, true) == 81)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day10")
    part1(input).println()
    part1(input, true).println()
}