import kotlin.math.pow

fun main() {
    fun generateOps(length: Int): List<IntArray> {
        val operations = length - 1
        var possibilities: MutableList<IntArray> = mutableListOf(intArrayOf(1), intArrayOf(2), intArrayOf(3))
        for (i in 1..<operations) {
            val newPossibilities = mutableListOf<IntArray>()
            for (possibility in possibilities) {
                val pos1 = (possibility + 1)
                val pos2 = (possibility + 2)
                val pos3 = possibility + 3
                newPossibilities.add((pos1))
                newPossibilities.add((pos2))
                newPossibilities.add((pos3))
            }
            possibilities = newPossibilities
        }
        return possibilities.toList()
    }
    fun part1(input: List<String>): Long {
        val equations: MutableList<Pair<Long, LongArray>> = mutableListOf()
        for (line in input) {
            val parsedLine = line.split(": ")
            equations.add(Pair(parsedLine[0].toLong(), parsedLine[1].split(" ").map { it.toLong() }.toLongArray()))
        }
        var sum: Long = 0
        equations@ for (equation in equations) {
            val operations = generateOps(equation.second.size)
            for (operation in operations) {
                var testStart = equation.second[0]
                for ((index, part) in operation.withIndex()) {
                    val element = equation.second[index+1]
                    if (part == 1) {
                        testStart += element
                    } else if (part == 2) {
                        testStart *= element
                    } else {
                        testStart = (testStart.toString() + element.toString()).toLong()
                    }
                }
                if (testStart == equation.first) {
                    sum += equation.first
                    continue@equations
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Test if implementation meets criteria from the description, like:

    // Or read a large test input from the `input/Day01_test.txt` file:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 11387.toLong())


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}