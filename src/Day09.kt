fun main() {
    fun part1(input: List<String>): Long {
        var fs: IntArray = intArrayOf()
        var id = 0
        for ((index, pos) in input[0].withIndex()) {
            if (index % 2 == 0) {
                for (i in 1..pos.digitToInt()) {
                    fs += id
                }
                id++
            } else {
                for (i in 1..pos.digitToInt()) {
                    fs += -1
                }
            }
        }
        while (fs.indexOfFirst { it == -1 } < fs.indexOfLast { it != -1 }) {
            val last = fs.indexOfLast { it != -1 }
            val firstFree = fs.indexOfFirst { it == -1 }
            fs[firstFree] = fs[last]
            fs[last] = -1
        }
        //fs += CharArray(freeSpace) {'.'}
        var checkSum = 0L
        for ((index, fileId) in fs.withIndex()) {
            if (fileId != -1) checkSum += index * fileId
        }
        return checkSum
    }

    fun part2(input: List<String>): Long {
        var fs: IntArray = intArrayOf()
        var id = 0
        for ((index, pos) in input[0].withIndex()) {
            if (index % 2 == 0) {
                for (i in 1..pos.digitToInt()) {
                    fs += id
                }
                id++
            } else {
                for (i in 1..pos.digitToInt()) {
                    fs += -1
                }
            }
        }

        var getFreeGroupSize = { index: Int ->
            var size = 1
            var i = index
            while (true) {
                i++
                if (i in fs.indices && fs[i] == fs[index]) size++
                else break
            }
            size
        }
        var getUsedGroupSize = { index: Int ->
            var size = 1
            var i = index
            while (true) {
                i--
                if (i in fs.indices && fs[i] == fs[index]) size++
                else break
            }
            size
        }

        val getEarliestWithSize = { index: Int ->
            val usedSize = getUsedGroupSize(index)
            var pos = -1
            var found = false
            var i = 0
            while (i < index) {
                if (fs[i] != -1) {
                    i++
                    continue
                }
                val freeSize = getFreeGroupSize(i)
                if (usedSize > freeSize) {
                    i += freeSize
                    continue
                }
                pos = i

                break
            }

            pos
    }
    id--
    while (true) {
        val usedPos = fs.indexOfLast { it == id }
        val freePos = getEarliestWithSize(usedPos)
        val usedSize = getUsedGroupSize(usedPos)
        if (freePos == -1) {
            id--
            if (id == -1) break
            continue
        }
        for (i in usedSize downTo 1) {
            val newUsed = freePos + i - 1
            val newFree = usedPos - i + 1
            fs[newUsed] = fs[newFree]
            fs[newFree] = -1
        }
        id--
        if (id == -1) break
    }
    var checkSum = 0L
    for ((index, fileId) in fs.withIndex()) {
        if (fileId != -1) checkSum += index * fileId
    }
    return checkSum
}
//fs += CharArray(freeSpace) {'.'}


val testInput = listOf("2333133121414131402")
// Test if implementation meets criteria from the description, like:
check(part1(testInput) == 1928L)
check(part2(testInput) == 2858L)

// Or read a large test input from the `input/Day01_test.txt` file:

// Read the input from the `src/Day01.txt` file.
val input = readInput("Day09")
part1(input).println()
part2(input).println()
}