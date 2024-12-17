import javax.swing.text.Position

fun main() {
    fun part1(input: List<String>): Int {
        var fishBowl: MutableList<CharArray> = mutableListOf()
        var instructions: CharArray = charArrayOf()
        for (line in input) {
            if (!line.isEmpty()) {
                val begin = line[0]
                when (begin) {
                    '#' -> fishBowl.add(line.toCharArray())
                    else -> instructions += line.toCharArray()
                }
            }
        }
        fun move(position: Pair<Int, Int>, direction: Pair<Int, Int>): Boolean {
            val currentChar = fishBowl[position]
            val nextPos = position + direction
            val nextSpace = fishBowl[nextPos]
            when (nextSpace) {
                '.' -> {
                    fishBowl[nextPos] = currentChar
                    fishBowl[position] = '.'
                    return true
                }

                'O' -> {
                    val okay = move(nextPos, direction)
                    if (okay) {
                        fishBowl[nextPos] = currentChar
                        fishBowl[position] = '.'
                        return true
                    }
                }

                '#' -> {
                    return false
                }
            }
            return false
        }
        for (instruction in instructions) {
            val direction: Pair<Int, Int>
            val height = fishBowl.indexOfFirst { it.contains('@') }
            val position = Pair(height, fishBowl[height].indexOfFirst { it == '@' })


            when (instruction) {
                '<' -> direction = Pair(0, -1)
                '^' -> direction = Pair(-1, 0)
                '>' -> direction = Pair(0, 1)
                'v' -> direction = Pair(1, 0)
                else -> error("Unexpected character $instruction")
            }
            move(position, direction)
        }
        var sum = 0
        for ((height, line) in fishBowl.withIndex()) {
            for ((width, element) in line.withIndex()) {
                if (element == 'O') sum += height * 100 + width
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var newMap: MutableList<CharArray> = mutableListOf()
        var instructions: CharArray = charArrayOf()
        for ((index, line) in input.withIndex()) {
            if (line.isNotEmpty()) {
                if (line[0] == '#') {
                    newMap.add(charArrayOf())
                    for (element in line) {
                        when (element) {
                            in listOf('.', '#') -> newMap[index] += charArrayOf(element, element)
                            'O' -> newMap[index] += charArrayOf('[', ']')
                            '@' -> newMap[index] += charArrayOf('@', '.')
                        }
                    }
                } else {
                    instructions += line.toCharArray()
                }
            }
        }



        fun move(position: Pair<Int, Int>, direction: Pair<Int, Int>, check: Boolean = true): Boolean {

            fun checkPartner(position: Pair<Int, Int>, direction: Pair<Int, Int>): Boolean {
                val nextPosition = position + direction
                val nextChar = newMap[nextPosition]
                when (nextChar) {
                    '.' -> return true
                    in listOf('[', ']') -> {
                        var lineCheck = nextPosition + direction
                        while (true) {
                            when (newMap[lineCheck]) {
                                '[', ']' -> lineCheck += direction
                                '#' -> return false
                                '.' -> break
                            }
                        }
                        val okay = move(nextPosition, direction, false)
                        if (okay) return true
                    }

                    '#' -> return false
                }
                return false
            }

            fun check(position: Pair<Int, Int>, direction: Pair<Int, Int>): Boolean {
                val nextPosition = position + direction
                val nextChar = newMap[nextPosition]
                if (direction.second != 0) return true
                when (nextChar) {
                    '.' -> return true
                    '[' -> {
                        val okay = check(nextPosition, direction) && check(nextPosition + Pair(0,1), direction)
                        if (okay) return true
                    }
                    ']' -> {
                        val okay = check(nextPosition, direction) && check(nextPosition + Pair(0,-1), direction)
                        if (okay) return true
                    }
                    '#' -> return false
                    else -> return false
                }
                return false
            }

            val currentChar = newMap[position]
            val nextPos = position + direction
            val nextSpace = newMap[nextPos]
            val doubleMode = currentChar in listOf('[', ']') && direction.first != 0
            var partner: Char? = null
            var partnerPosition: Pair<Int, Int>? = null
            var pairPosition: Pair<Pair<Int, Int>, Pair<Int, Int>>? = null
            if (doubleMode) {
                if (currentChar == '[') {
                    partner = ']'
                    partnerPosition = position + Pair(0, 1)
                    pairPosition = Pair(position, partnerPosition)
                } else {
                    partner = '['
                    partnerPosition = position + Pair(0, -1)
                    pairPosition = Pair(partnerPosition, position)
                }
            }
            when (nextSpace) {
                '.' -> {
                    if (doubleMode) {
                        val partnerOkay = checkPartner(partnerPosition!!, direction)
                        if (partnerOkay) {
                            newMap[pairPosition!!.first + direction] = '['
                            newMap[pairPosition.second + direction] = ']'
                            newMap[pairPosition] = Pair('.', '.')
                            return true
                        } else return false
                    }
                    newMap[nextPos] = currentChar
                    newMap[position] = '.'
                    return true
                }

                in listOf('[', ']') -> {

                    if (doubleMode) {
                        if (nextSpace == currentChar) {
                            val okay = move(nextPos, direction)
                            if (okay) {
                                newMap[pairPosition!!.first + direction] = '['
                                newMap[pairPosition.second + direction] = ']'
                                newMap[pairPosition] = Pair('.', '.')
                                return true
                            } else return false
                        } else {
                            val partnerOkay = checkPartner(partnerPosition!!, direction)
                            if (partnerOkay) {
                                val okay = move(nextPos, direction)
                                if (okay) {
                                    newMap[pairPosition!!.first + direction] = '['
                                    newMap[pairPosition.second + direction] = ']'
                                    newMap[pairPosition] = Pair('.', '.')
                                    return true
                                } else return false
                            } else return false
                        }
                    }
                    if (check) {
                        if (!check(position, direction)) return false
                    }
                    val okay = move(nextPos, direction, false)
                    if (okay) {
                        newMap[nextPos] = currentChar
                        newMap[position] = '.'
                        return true
                    }
                }

                '#' -> {
                    return false
                }
            }
            return false
        }

        for (instruction in instructions) {
            val height = newMap.indexOfFirst { it.contains('@') }
            val position = Pair(height, newMap[height].indexOfFirst { it == '@' })
            val direction: Pair<Int, Int>
            when (instruction) {
                '<' -> direction = Pair(0, -1)
                '^' -> direction = Pair(-1, 0)
                '>' -> direction = Pair(0, 1)
                'v' -> direction = Pair(1, 0)
                else -> error("Unexpected character $instruction")
            }
            move(position, direction)
        }
        var sum = 0
        for ((height, line) in newMap.withIndex()) {
            for ((width, element) in line.withIndex()) {
                if (element == '[') {
                    sum += height * 100 + width
                }
            }
        }
        return sum
    }

    // Or read a large test input from the `input/Day01_test.txt` file:
    val testInput = readInput("Day15_test")

    //part2(testInput)
    //check(part1(testInput) == 10092)
    //check(part2(testInput) == 9021)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}


