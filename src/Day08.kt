fun main() {
    fun part1(input: List<String>): Int {
        val input = input.map { it.map { it }.toCharArray() }
        val matches = input.map { it.toList().toCharArray()}
        for ((lineIndex, line) in input.withIndex()) {
            for ((colIndex, signal) in line.withIndex()) {
                if (signal == '.' || signal == '#') continue

                for ((checkIndex, checkLine) in input.withIndex()) {
                    for ((checkIndex2, checkChar) in checkLine.withIndex()) {
                        if (checkChar == signal && lineIndex != checkIndex && colIndex != checkIndex2) {
                            val newLine: Int = if (lineIndex > checkIndex) lineIndex - (lineIndex - checkIndex) * 2
                            else lineIndex + (checkIndex - lineIndex) * 2
                            val newCol: Int = if (colIndex > checkIndex2) colIndex - (colIndex - checkIndex2) * 2
                            else colIndex + (checkIndex2 - colIndex) * 2

                            if (newLine in input.indices && newCol in input[0].indices) matches[newLine][newCol] = '#'

                        }
                    }

                }
            }

        }
        val result = matches.flatMap { it.toList() }.count { it == '#' }
        return result
    }

    fun part2(input: List<String>): Int {
        val input = input.map { it.map { it }.toCharArray() }
        val matches = input.map { it.toList().toCharArray()}
        for ((lineIndex, line) in input.withIndex()) {
            for ((colIndex, signal) in line.withIndex()) {
                if (signal == '.' || signal == '#') continue

                for ((checkIndex, checkLine) in input.withIndex()) {
                    for ((checkIndex2, checkChar) in checkLine.withIndex()) {
                        if (checkChar == signal && lineIndex != checkIndex && colIndex != checkIndex2) {
                            matches[checkIndex][checkIndex2] = '#'
                            var newLine = lineIndex
                            var newCol = colIndex
                            var inBound = true
                            do {
                                newLine = if (lineIndex > checkIndex) newLine + (lineIndex - checkIndex)
                                else newLine - (checkIndex - lineIndex)
                                newCol = if (colIndex > checkIndex2) newCol + (colIndex - checkIndex2)
                                else newCol - (checkIndex2 - colIndex)

                                if (newLine in input.indices && newCol in input[0].indices) matches[newLine][newCol] =
                                    '#'
                                else inBound = false
                            } while (inBound)

                        }
                    }

                }
            }

        }
        val result = matches.flatMap { it.toList() }.count { it == '#' }
        return result
    }


    // Or read a large test input from the `input/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}