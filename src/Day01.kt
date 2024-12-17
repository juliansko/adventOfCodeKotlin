import kotlin.math.abs

fun main() {
    fun organize(input: List<String>): Pair<MutableList<Int>,MutableList<Int>> {
        val col1: MutableList<Int> = mutableListOf()
        val col2: MutableList<Int> = mutableListOf()
        for (line in input) {
            val numbers = line.split("   ").map { it.toInt()}
            col1.add(numbers.first())
            col2.add(numbers.last())
        }
        return Pair(col1, col2)
    }

    fun part1(input: List<String>): Int {
        val (col1, col2) = organize(input)
        col1.sort()
        col2.sort()
        var sum = 0
        for ((index, value) in col1.withIndex()) {
            sum += abs(value - col2[index])
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val (col1, col2) = organize(input)
        var sum = 0
        for ((index, value) in col1.withIndex()) {
            sum += value * col2.count { it == value }
        }
        return sum
    }


    // Or read a large test input from the `src/Day01_test.txt.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
