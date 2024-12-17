fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        val matcher = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        val string = input.joinToString("")
        val matches = matcher.findAll(string)
        for (match in matches) {
            sum += match.groupValues[1].toInt() * match.groupValues[2].toInt()
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        var enabled = true
        val string = input.joinToString("")
        val matcher = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)")
        val matches = matcher.findAll(string)
        for (match in matches) {
            println(match.value)
            when (match.value) {
                "do()" -> enabled = true
                "don't()" -> enabled = false
                else -> {
                    if (enabled) {
                        sum += match.groupValues[1].toInt() * match.groupValues[2].toInt()
                    }
                }
            }
        }
        return sum
    }



    // Or read a large test input from the `src/Day01_test.txt.txt` file:
    val testInput1 = listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")
    check(part1(testInput1) == 161)

    val testInput2 = listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")
    check(part2(testInput2) == 48)
    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}