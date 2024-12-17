import java.lang.IndexOutOfBoundsException

fun main() {
    fun checkMas(message: MutableList<CharArray>, position: Pair<Int, Int>, movement: Pair<Int, Int>): Boolean {
        try {
            if (message[position.second + movement.second][position.first + movement.first] == 'M') {
                if (message[position.second + movement.second * 2][position.first + movement.first * 2] == 'A') {
                    if (message[position.second + movement.second * 3][position.first + movement.first * 3] == 'S') {
                        return true
                    }
                }
            }
            return false
        } catch (e: ArrayIndexOutOfBoundsException) {
            println(position)
            return false
        }
    }

    fun part1(input: List<String>): Int {
        val message: MutableList<CharArray> = mutableListOf()
        for (line in input) {
            message.add(line.toCharArray())
        }
        var count = 0
        val width = message[0].size
        val height = message.size
        for ((lineIndex, line) in message.withIndex()) {
            val canHigher = lineIndex > 2
            val canLower = lineIndex < height - 3
            for ((charIndex, char) in line.withIndex()) {
                val canLeft = charIndex > 2
                val canRight = charIndex < width - 3
                if (char == 'X') {
                    if (canLeft) {
                        if (canHigher) {
                            if(checkMas(message, Pair(charIndex, lineIndex), Pair(-1, -1))) count++
                        }
                        if (canLower) {
                            if(checkMas(message, Pair(charIndex, lineIndex), Pair(-1, 1))) count++
                        }
                        if(checkMas(message, Pair(charIndex, lineIndex), Pair(-1, 0))) count++
                    }
                    if (canRight) {
                        if (canLower) {
                            if(checkMas(message, Pair(charIndex, lineIndex), Pair(1, 1))) count++
                        }
                        if (canHigher) {
                            if(checkMas(message, Pair(charIndex, lineIndex), Pair(1, -1))) count++
                        }
                        if(checkMas(message, Pair(charIndex, lineIndex), Pair(1, 0))) count++
                    }
                    if (canHigher) {
                        if(checkMas(message, Pair(charIndex, lineIndex), Pair(0, -1))) count++
                    }
                    if (canLower) {
                        if(checkMas(message, Pair(charIndex, lineIndex), Pair(0, 1))) count++
                    }
                }
            }
        }

        return count
    }


    fun part2(input: List<String>): Int {
        val message: MutableList<CharArray> = mutableListOf()
        for (line in input) {
            message.add(line.toCharArray())
        }
        var count = 0
        for ((lineIndex, line) in message.withIndex()) {
            for ((charIndex, char) in line.withIndex()) {
                try {
                    if (char == 'A') {
                        when (message[lineIndex - 1][charIndex - 1]) {
                            'M' -> if (message[lineIndex - 1][charIndex + 1] == 'M') {
                                if (message[lineIndex + 1][charIndex + 1] == 'S' && message[lineIndex + 1][charIndex - 1] == 'S') {
                                    count++
                                }
                            } else if (message[lineIndex + 1][charIndex - 1] == 'M') {
                                if (message[lineIndex + 1][charIndex + 1] == 'S' && message[lineIndex - 1][charIndex + 1] == 'S') {
                                    count++
                                }
                            }

                            'S' -> if (message[lineIndex - 1][charIndex + 1] == 'S') {
                                if (message[lineIndex + 1][charIndex + 1] == 'M' && message[lineIndex + 1][charIndex - 1] == 'M') {
                                    count++
                                }
                            } else if (message[lineIndex + 1][charIndex - 1] == 'S') {
                                if (message[lineIndex + 1][charIndex + 1] == 'M' && message[lineIndex - 1][charIndex + 1] == 'M') {
                                    count++
                                }
                            }

                            else -> continue
                        }
                    }
                }
                catch (e: ArrayIndexOutOfBoundsException) {
                    continue
                }
                catch (e: IndexOutOfBoundsException) {
                    continue
                }
            }
        }
        return count
    }


    // Or read a large test input from the `src/Day01_test.txt.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from the `src/Day04.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}