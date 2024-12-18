fun main() {


    fun List<List<String>>.check(coords: Pair<Int, Int>): Boolean {
        return coords.first in this.indices && coords.second in this[coords.first].indices
    }
    fun part1(input: List<String>, size: Int = 71, byteFall: Int = 1024): Int {
        val byteLine = MutableList(size) { "." }.toList()
        var byteMap = MutableList(size) { byteLine.map { it }.toMutableList() }
        for (i in 1..byteFall) {
            val bytes = input[i - 1].split(",").map { it.toInt() }
            val bytePos = Pair(bytes[1], bytes[0])
            byteMap[bytePos] = "#"
        }
        var scores: IntArray = intArrayOf()
        val start = Pair(0, 0)
        fun solve(path: Int = 0, direction: Pair<Int, Int> = Pair(1, 0), position: Pair<Int, Int> = Pair(0,0)) {
            var pos = position
            var localScore = path
            while (true) {
                byteMap[pos] = localScore.toString()
                if (pos == Pair(size - 1, size - 1)) {
                    scores += localScore
                    break
                }
                val neighbours = listOf( Pair(direction.flip(), pos + direction.flip()), Pair(direction.flipNegative(), pos + direction.flipNegative()))
                for (neighbour in neighbours) {
                    if (byteMap.check(neighbour.second) && (byteMap[neighbour.second] == "." || (byteMap[neighbour.second] != "#" && byteMap[neighbour.second].toInt() > localScore + 1))) {
                        byteMap[pos] = localScore.toString()
                        solve(localScore + 1, neighbour.first, neighbour.second)
                        continue
                    }
                }
                val nextPos = pos + direction
                if (!byteMap.check(nextPos)) break
                when (byteMap[nextPos]) {
                    "." -> {
                        localScore++
                        pos = nextPos
                        continue
                    }
                    "#" -> break
                    else -> {
                        val nextScore = byteMap[nextPos].toInt()
                        if (localScore + 1 < nextScore) {
                            localScore++
                            pos = nextPos
                            continue
                        } else break
                    }
                }
            }
        }
        solve()
        return scores.min()
    }

    fun part2(input: List<String>, size: Int = 71,  byteFall: Int = 1024):String {
        val byteLine = MutableList(size) { "." }.toList()
        var byteMapPrint = MutableList(size) { byteLine.map { it }.toMutableList() }
        var byteMap: MutableList<MutableList<String>> = mutableListOf()
        for (i in 1..byteFall) {
            val bytes = input[i - 1].split(",").map { it.toInt() }
            val bytePos = Pair(bytes[1], bytes[0])
            byteMapPrint[bytePos] = "#"
        }
        var scores: IntArray = intArrayOf()
        val start = Pair(0, 0)
        fun solve(path: Int = 0, direction: Pair<Int, Int> = Pair(1, 0), position: Pair<Int, Int> = Pair(0,0)) {
            var pos = position
            var localScore = path
            while (true) {
                byteMap[pos] = localScore.toString()
                if (pos == Pair(size - 1, size - 1)) {
                    scores += localScore
                    break
                }
                val neighbours = listOf( Pair(direction.flip(), pos + direction.flip()), Pair(direction.flipNegative(), pos + direction.flipNegative()))
                for (neighbour in neighbours) {
                    if (byteMap.check(neighbour.second) && (byteMap[neighbour.second] == "." || (byteMap[neighbour.second] != "#" && byteMap[neighbour.second].toInt() > localScore + 1))) {
                        byteMap[pos] = localScore.toString()
                        solve(localScore + 1, neighbour.first, neighbour.second)
                        continue
                    }
                }
                val nextPos = pos + direction
                if (!byteMap.check(nextPos)) break
                when (byteMap[nextPos]) {
                    "." -> {
                        localScore++
                        pos = nextPos
                        continue
                    }
                    "#" -> break
                    else -> {
                        val nextScore = byteMap[nextPos].toInt()
                        if (localScore + 1 < nextScore) {
                            localScore++
                            pos = nextPos
                            continue
                        } else break
                    }
                }
            }
        }
        var coords: String = ""
        for (i in byteFall  until input.size) {
            val bytes = input[i].split(",").map { it.toInt() }
            val bytePos = Pair(bytes[1], bytes[0])
            byteMapPrint[bytePos] = "#"
            byteMap = byteMapPrint.map { it.map { it }.toMutableList() }.toMutableList()
            scores = intArrayOf()
            solve()
            if (byteMap[Pair(size - 1, size - 1)] == ".") {
                coords = input[i]
                break
            }
        }
        return coords
    }

    // Or read a large test input from the `input/Day01_test.txt` file:
    val testInput = readInput("Day18_test")
    check(part1(testInput, 7, 12) == 22)
    check(part2(testInput, 7, 12) == "6,1")

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day18")
    part1(input).println()
    part2(input).println()
}