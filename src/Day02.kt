import kotlin.math.abs

fun main() {
    fun organize(input: List<String>): MutableList<List<Int>> {
        val reports: MutableList<List<Int>> = mutableListOf()
        for (line in input) {
            val report = line.split(" ").map { it.toInt() }
            reports.add(report)
        }
        return reports
    }

    fun part1(input: List<String>): Int {
        val reports = organize(input)

        var safe = 0
        var prev: Int? = null
        var increasing: Boolean
        loop@ for (report in reports) {
            increasing = report[0] - report[1] < 0
            for (num in report) {
                if (prev != null) {
                    val inc = prev - num < 0
                    val diff = abs(num - prev)
                    if (diff < 1 || diff > 3 || inc != increasing) {
                        prev = null
                        continue@loop
                    }
                }
                prev = num
            }
            prev = null
            safe++
        }

        return safe
    }

    fun part2(input: List<String>): Int {
        val reports = organize(input)
        var safe = 0
        var prev: Int? = null
        var increasing: Boolean
        val reset = {
            prev = null
        }
        var inc: Boolean
        loop@ for (report in reports) {
            increasing = report[0] - report[1] < 0
            inner@ for (num in report) {
                if (prev != null) {
                    inc = prev!! - num < 0
                    val diff = abs(num - prev!!)
                    if (diff < 1 || diff > 3 || inc != increasing) {
                        reset()
                        removedinner@ for (i in 0..<report.size) {
                            val removed = report.filterIndexed { index, _ -> index != i }
                            increasing = removed[0] - removed[1] < 0
                            for (renum in removed) {
                                if (prev != null) {
                                    inc = prev!! - renum < 0
                                    val diff = abs(renum - prev!!)
                                    if (diff < 1 || diff > 3 || inc != increasing) {
                                        reset()
                                        continue@removedinner
                                    }
                                }
                                prev = renum
                            }
                            break@inner
                        }
                        continue@loop
                    }
                }
                prev = num
            }
            reset()
            safe++
        }

        return safe
    }


    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}