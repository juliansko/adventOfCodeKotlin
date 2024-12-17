import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {

        val garden = input.map { it.toCharArray() }
        val checkedField: MutableList<Pair<Int, Int>> = mutableListOf()
        val directions = listOf(Pair(1, 0), Pair(0, 1), Pair(-1, 0), Pair(0, -1))
        fun checkNeighbours(field: Pair<Int, Int>, areaBorder: IntArray): IntArray {
            var areaBorders = areaBorder
            areaBorders[0]++
            checkedField.add(field)
            val results: MutableList<Pair<Int, Int>> = mutableListOf()
            val plant = garden[field.first][field.second]
            for (direction in directions) {
                val comp = Pair(field.first + direction.first, field.second + direction.second)
                if (comp.first in garden.indices && comp.second in garden[0].indices) {
                    if (garden[comp.first][comp.second] == plant) {
                        if (comp in checkedField) continue
                        results.add(comp)
                        checkedField.add(comp)
                        continue
                    }
                }
                areaBorders[1]++
            }
            for (result in results) {
                areaBorders = checkNeighbours(result, areaBorders)
            }
            return areaBorders
        }

        var sum = 0
        for ((lineIndex, line) in garden.withIndex()) {
            for (colIndex in line.indices) {
                val position = Pair(lineIndex, colIndex)
                if (position !in checkedField) {
                    val materials = checkNeighbours(position, intArrayOf(0, 0))
                    sum += materials[0] * materials[1]
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {

        val garden = input.map { it.toCharArray() }
        val checkedField: MutableList<Pair<Int, Int>> = mutableListOf()
        val directions = listOf(Pair(1, 0), Pair(0, 1), Pair(-1, 0), Pair(0, -1))
        val diagonals = listOf(Pair(1, 1), Pair(1, -1), Pair(-1, 1), Pair(-1, -1))
        val borders: MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>> = mutableListOf()
        var areas = 0
        fun checkNeighbours(field: Pair<Int, Int>) {
            val results: MutableList<Pair<Int, Int>> = mutableListOf()
            val plant = garden[field.first][field.second]
            var bords = 0
            for (direction in directions) {
                val comp = Pair(field.first + direction.first, field.second + direction.second)
                if (comp.first in garden.indices && comp.second in garden[0].indices) {
                    if (garden[comp.first][comp.second] == plant) {
                        if (comp in checkedField) continue
                        results.add(comp)
                        checkedField.add(comp)
                        areas++
                        continue
                    }
                }
                val dir = 1 - abs(direction.first)
                val border = Pair(field, direction)
                if (border !in borders) borders.add(border)
            }

            for (result in results) {
                checkNeighbours(result)
            }
        }

        var sum = 0
        for ((lineIndex, line) in garden.withIndex()) {
            for (colIndex in line.indices) {
                val position = Pair(lineIndex, colIndex)
                if (position !in checkedField) {
                    checkNeighbours(position)
                    var sides = 0
                    for (border in borders) {
                        val comp1 = Pair(Pair(border.first.first + border.second.second,border.first.second + border.second.first), border.second)
                        val comp2 = Pair(Pair(border.first.first - border.second.second,border.first.second - border.second.first), border.second)
                        if( comp1 in borders) {
                            println(comp1)
                        } else sides++
                        if (comp2 in borders) {
                            println(comp2)
                        } else sides++
                    }
                    // sides /= 2
                    //if (sides % 2 == 1) sides++
                    if (areas == 0) areas++
                    sum += areas * sides / 2
                    areas = 0
                    borders.clear()
                }
            }
        }
        return sum
    }

    // Test if implementation meets criteria from the description, like:

    // Or read a large test input from the `input/Day01_test.txt` file:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 1930)
    check(part2(testInput) == 1206)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
