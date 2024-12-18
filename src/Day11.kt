fun main() {
    fun part1(input: List<String>, blinks: Int): Long {
        var stones: HashMap<Long, Long> = hashMapOf()
        val inputs = input[0].split(" ").map { it.toLong() }
        for (stone in inputs) {
            stones[stone] = 1
        }
        for (i in 1..blinks) {
            val newStones: HashMap<Long, Long> = hashMapOf()
            for (stone in stones) {
                if (stone.key == 0L) {
                    newStones.add(1L, stone.value)
                    continue
                }
                if (stone.key.toString().length % 2 == 0) {
                    val left = stone.key.toString().substring(0, stone.key.toString().length / 2).toLong()
                    val right = stone.key.toString().substring(stone.key.toString().length / 2).toLong()
                    newStones.add(left, stone.value)
                    newStones.add(right, stone.value)
                    continue
                }
                newStones.add(stone.key*2024L, stone.value)
            }
            stones = newStones
            println("Blink no: $i")
        }
        return stones.values.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Test if implementation meets criteria from the description, like:

    // Or read a large test input from the `input/Day01_test.txt` file:
    val testInput = listOf("125 17")
    check(part1(testInput, 25) == 55312L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day11")
    part1(input, 25).println()
    part1(input, 75).println()
}