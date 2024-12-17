import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Path(var position: Pair<Int, Int>, var score: Int, val direction: Pair<Int, Int>, val journey: MutableList<Pair<Int, Int>> = mutableListOf())

fun main() {
    fun part1(input: List<String>): Int {
        var lab = input.map { it.toCharArray() }
        var arrows: HashMap<Pair<Int, Int>, Int> = hashMapOf()
        var scores: IntArray = intArrayOf()
        var lowScore = 0
        var startingHeight = lab.indexOfFirst { it.contains('S') }
        var startingPoint = Pair(startingHeight, lab[startingHeight].indexOfFirst { it == 'S' })
        fun solve(path: Path) {
            while (true) {
                val nextPos = path.position + path.direction
                when (lab[nextPos]) {
                    'E' -> {
                        scores += path.score + 1
                        break
                    }

                    '.' -> {
                        if (!arrows.containsKey(path.position) || arrows[path.position]!! > path.score) arrows[path.position] = path.score
                        path.score += 1
                        path.position = nextPos
                        if (arrows.contains(path.position) && arrows[path.position]!! <= path.score) {
                            break
                        }
                        val neighbours =
                            listOf(path.position + path.direction.flip(), path.position + path.direction.flipNegative())
                        for (neighbour in neighbours) {
                            if (lab[neighbour] == '.') {
                                val newDirection = neighbour - path.position
                                if (!arrows.containsKey(neighbour) || arrows[neighbour]!! > path.score + 1000) {
                                    solve(Path(path.position, path.score + 1000, newDirection))
                                }
                            }
                        }
                    }

                    '#' -> break

                }
            }
        }
        solve(Path(startingPoint, 1000, Pair(-1, 0)))
        lowScore = scores.min()
        return lowScore
    }

    fun part2(input: List<String>): Int {
        var lab = input.map { it.toCharArray() }
        var arrows: HashMap<Pair<Int, Int>, Int> = hashMapOf()
        var scores: IntArray = intArrayOf()
        val finalRuns: MutableList<Path> = mutableListOf()
        var lowScore = 0
        var startingHeight = lab.indexOfFirst { it.contains('S') }
        var startingPoint = Pair(startingHeight, lab[startingHeight].indexOfFirst { it == 'S' })
        fun solve(path: Path) {
            while (true) {
                path.journey.add(path.position)
                val nextPos = path.position + path.direction
                when (lab[nextPos]) {
                    'E' -> {
                        path.score += 1
                        path.journey.add(nextPos)
                        finalRuns.add(path)
                        break
                    }

                    '.' -> {
                        if (!arrows.containsKey(path.position) || arrows[path.position]!! > path.score) arrows[path.position] = path.score
                        path.score += 1
                        path.position = nextPos
                        if (arrows.contains(path.position) && arrows[path.position]!! + 2000 < path.score) {
                            break
                        }
                        val neighbours =
                            listOf(path.position + path.direction.flip(), path.position + path.direction.flipNegative())
                        for (neighbour in neighbours) {
                            if (lab[neighbour] == '.') {
                                val newDirection = neighbour - path.position
                                if (!arrows.containsKey(neighbour) || arrows[neighbour]!! > path.score + 1000) {
                                    solve(Path(path.position, path.score + 1000, newDirection, path.journey.map { it }.toMutableList()))
                                }
                            }
                        }
                    }

                    '#' -> break

                }
            }
        }
        solve(Path(startingPoint, 1000, Pair(-1, 0)))
        lowScore = finalRuns.minOf { it.score }
        val fastest = finalRuns.filter { it.score == lowScore }
        val seats = fastest.map { it.journey }.flatten().distinct()
        return seats.size
    }


    // Or read a large test input from the `input/Day01_test.txt` file:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 11048)
    check(part2(testInput) == 64)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}