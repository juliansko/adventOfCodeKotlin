fun main() {
    fun part1(input: List<String>): Int {
        var rules: MutableList<IntArray> = mutableListOf()
        var updates: MutableList<IntArray> = mutableListOf()
        var legitUpdates: MutableList<IntArray> = mutableListOf()
        var sum = 0

        for (line in input) {
            if (line.contains("|")) {
                rules.add(line.split("|").map { it.toInt() }.toIntArray())
                continue
            } else if (line.contains(",")) {
                updates.add(line.split(",").map { it.toInt() }.toIntArray())
            }
        }

        updates@ for (update in updates) {
            for ((index, value) in update.withIndex()) {
                val blockers = rules.filter { it[1] == value }
                for (blocker in blockers) {
                    if (update.contains(blocker[0]) && !update.slice(0..<index).contains(blocker[0])) {
                        continue@updates
                    }
                }
            }
            legitUpdates.add(update)
        }
        for (update in legitUpdates) {
            val middle = update.size / 2
            sum += update[middle]
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val rules: MutableList<IntArray> = mutableListOf()
        val updates: MutableList<IntArray> = mutableListOf()
        val falseUpdates: MutableList<IntArray> = mutableListOf()
        var sum = 0

        for (line in input) {
            if (line.contains("|")) {
                rules.add(line.split("|").map { it.toInt() }.toIntArray())
                continue
            } else if (line.contains(",")) {
                updates.add(line.split(",").map { it.toInt() }.toIntArray())
            }
        }
        var error = true
        updates@ for (update in updates) {
            error = false
            outer@ while (true) {
                for ((index, value) in update.withIndex()) {
                    val blockers = rules.filter { it[1] == value }

                    for (blocker in blockers) {
                        val prevUpdate = blocker[0]
                        for ((blockdex, block) in update.withIndex()) {
                            if (block == prevUpdate && blockdex > index) {
                                error = true
                                update[index] = update[blockdex].also { update[blockdex] = update[index] }
                                continue@outer
                            }
                        }
                    }
                }
                break@outer
            }
            if (error) falseUpdates.add(update)
        }


        for (update in falseUpdates) {
            val middle = update.size / 2
            sum += update[middle]
        }
        return sum
    }

    // Or read a large test input from the `src/Day01_test.txt.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}