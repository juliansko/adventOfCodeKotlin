import kotlin.math.pow

fun main() {

    fun part1(input: List<String>, a: Long = 0): IntArray {
        var registerA = 0L
        if (a == 0L) registerA = input[0].split(": ")[1].toLong()
        if (a > 0) registerA = a
        var registerB = 0L
        var registerC = 0L
        var instructions = input[4].split(": ")[1].split(",").map { it.toInt() }.toIntArray()
        var instructionPointer = 0
        var output = intArrayOf()
        val comboOperand: (Int) -> Long = { it: Int ->
            when (it) {
                0, 1, 2, 3 -> it.toLong()
                4 -> registerA.toLong()
                5 -> registerB.toLong()
                6 -> registerC
                else -> throw IllegalArgumentException()
            }
        }

        while (instructionPointer < instructions.size - 1) {
            val operand = instructions[instructionPointer + 1]
            when (instructions[instructionPointer]) {
                0 -> registerA /= 2f.pow(comboOperand(operand).toInt()).toLong()
                1 -> registerB = registerB xor operand.toLong()
                2 -> registerB = comboOperand(operand) % 8
                3 -> if (registerA != 0L) {
                    instructionPointer = operand
                    continue
                }

                4 -> registerB = registerB xor registerC
                5 -> output += (comboOperand(operand) % 8).toInt()
                6 -> registerB = registerA / 2f.pow(comboOperand(operand).toInt()).toLong()
                7 -> registerC = registerA / 2f.pow(comboOperand(operand).toInt()).toLong()
            }
            instructionPointer += 2
        }


        return output
    }

    fun part2(input: List<String>): Long {
        val program = input[4].split(": ")[1].split(",").map { it.toInt() }.toIntArray()
        var output: IntArray = intArrayOf()
        var start = 0L
        var bin = ""
        instructions@ for (instruction in program.reversed()) {
            if (output.size == 15) {
                // bin = start.toString(2)
                println()
            }
            var i = "${bin}000".toLong(2)
            while (true) {
                var b = i % 8
                b = b xor 1
                val c = i / 2.toDouble().pow(b.toInt()).toLong()
                b = b xor 5
                b = b xor c
                val out = b % 8
                if (out == instruction.toLong()) {
                    bin = "${bin}${Integer.toBinaryString((i % 8).toInt()).padStart(3, '0')}"
                    output = part1(input, i)
                    start = "${bin}000".toLong(2)
                    continue@instructions
                }
                i++

            }
        }

        println()
        var res = bin.toLong(2)
        while (true) {
           if (part1(input, res).contentEquals(program)) break
           res++
        }
        return res
    }

    // Or read a large test input from the `input/Day01_test.txt` file:
    val testInput = readInput("Day17_test")
    check(part1(testInput).contentEquals(intArrayOf(4, 6, 3, 5, 6, 3, 5, 2, 1, 0)))
    //val test2 = readInput("Day17_test2")
    //check(part2(test2) == 117440L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day17")
    part1(input).joinToString(",").println()
    part2(input).println()
}