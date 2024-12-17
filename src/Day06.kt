fun main() {
    fun part1(input: List<String>): Int {
        val map: MutableList<CharArray> = mutableListOf()
        for (line in input) {
            map.add(line.toCharArray())
        }
        var location: Pair<Int, Int> = Pair(0, 0)
        var direction = Pair(-1, 0)
       for ((index, line) in map.withIndex()) {
           if (line.contains('^')) {
               for ((x,char) in line.withIndex()) {
                   if (char == '^') {
                       location = Pair(index, x)
                       break
                   }
               }
               break
           }
       }
        val boundaries = Pair(map.size, map[0].size)
        do {
            var character = map[location.first][location.second]
            var newLocation = Pair(location.first + direction.first, location.second + direction.second)
            if (newLocation.first >= 0 && newLocation.first < boundaries.first && newLocation.second >= 0 && newLocation.second < boundaries.second) {
                if (map[newLocation.first][newLocation.second] != '#') {
                    map[newLocation.first][newLocation.second] = character
                    map[location.first][location.second] = 'X'
                    location = newLocation
                } else {
                    when (character) {
                        '^' -> {
                            map[location.first][location.second] = '>'
                            direction = Pair(0, 1)
                        }

                        '>' -> {
                            map[location.first][location.second] = 'v'
                            direction = Pair(1, 0)
                        }

                        'v' -> {
                            map[location.first][location.second] = '<'
                            direction = Pair(0, -1)
                        }

                        '<' -> {
                            map[location.first][location.second] = '^'
                            direction = Pair(-1, 0)
                        }
                        else -> return -1
                    }
                }
            } else {
                map[location.first][location.second] = 'X'
                break
            }
        } while (true) 
        
        val fields = map.fold(0) { acc, chars -> acc + chars.count { it == 'X' } }
        return fields
    }

    data class Collision(val position: Pair<Int, Int>, val direction: Pair<Int, Int>)

    fun part2(input: List<String>): Int {
        val map: MutableList<CharArray> = mutableListOf()
        for (line in input) {
            map.add(line.toCharArray())
        }
        var location: Pair<Int, Int> = Pair(0, 0)
        var direction = Pair(-1, 0)
        for ((index, line) in map.withIndex()) {
            if (line.contains('^')) {
                for ((x,char) in line.withIndex()) {
                    if (char == '^') {
                        location = Pair(index, x)
                        break
                    }
                }
                break
            }
        }
        val boundaries = Pair(map.size, map[0].size)
        for (i in 0..<boundaries.first) {
            for (j in 0..<boundaries.second) {
                var varMap = map.map { it.toList().toCharArray() }.toMutableList()
                var varLoc = Pair(location.first, location.second)
                var varDir = Pair(direction.first, direction.second)
                var collisions: MutableList<Collision> = mutableListOf()
                if(varMap[i][j] != '^' && varMap[i][j] != '#') {
                    varMap[i][j] = '#'
                    do {
                        var character = varMap[varLoc.first][varLoc.second]
                        var newLocation = Pair(varLoc.first + varDir.first, varLoc.second + varDir.second)
                        if (newLocation.first >= 0 && newLocation.first < boundaries.first && newLocation.second >= 0 && newLocation.second < boundaries.second) {
                            if (varMap[newLocation.first][newLocation.second] != '#') {
                                varMap[newLocation.first][newLocation.second] = character
                                varLoc = newLocation
                            } else {
                                val collision = Collision(varLoc, varDir)
                                if (collisions.contains(collision)) {
                                    map[i][j] = 'O'
                                    break
                                }
                                collisions.add(collision)
                                when (character) {
                                    '^' -> {
                                        varMap[varLoc.first][varLoc.second] = '>'
                                        varDir = Pair(0, 1)
                                    }

                                    '>' -> {
                                        varMap[varLoc.first][varLoc.second] = 'v'
                                        varDir = Pair(1, 0)
                                    }

                                    'v' -> {
                                        varMap[varLoc.first][varLoc.second] = '<'
                                        varDir = Pair(0, -1)
                                    }

                                    '<' -> {
                                        varMap[varLoc.first][varLoc.second] = '^'
                                        varDir = Pair(-1, 0)
                                    }
                                    else -> return -1
                                }
                            }
                        } else {
                            break
                        }
                    } while (true)
                }
            }
        }
        val obstacles = map.fold(0) { acc, chars -> acc + chars.count { it == 'O'} }

        return obstacles
    }


   // Or read a large test input from the `src/Day01_test.txt.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}