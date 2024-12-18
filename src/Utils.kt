import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("input/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

public operator fun Pair<Int, Int>.plus(secondPair: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first + secondPair.first, this.second + secondPair.second)
}

public operator fun Pair<Int, Int>.minus(secondPair: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first - secondPair.first, this.second - secondPair.second)
}

public operator fun Pair<Int, Int>.times(factor: Int): Pair<Int, Int> {
    return Pair(this.first * factor, this.second * factor)
}

public operator fun List<CharArray>.get(coords: Pair<Int, Int>): Char {
    return this[coords.first][coords.second]
}

public operator fun List<IntArray>.get(coords: Pair<Int, Int>): Int {
    return this[coords.first][coords.second]
}

public operator fun List<CharArray>.set(coords: Pair<Int, Int>, char: Char) {
    this[coords.first][coords.second] = char
}

public operator fun List<List<String>>.get(coords: Pair<Int, Int>): String {
    return this[coords.first][coords.second]
}

public operator fun List<MutableList<String>>.set(coords: Pair<Int, Int>, str: String) {
    this[coords.first][coords.second] = str
}

public operator fun List<CharArray>.get(coords: Pair<Pair<Int, Int>, Pair<Int, Int>>): Pair<Char, Char> {
    return Pair(this[coords.first], this[coords.second])
}

public operator fun List<CharArray>.set(coords: Pair<Pair<Int, Int>, Pair<Int, Int>>, chars: Pair<Char, Char>) {
    this[coords.first] = chars.first
    this[coords.second] = chars.second
}

fun Pair<Int, Int>.flip(): Pair<Int, Int> {
    return Pair(this.second, this.first)
}

fun Pair<Int, Int>.flipNegative(): Pair<Int, Int> {
    return Pair(-this.second, -this.first)
}

fun mapDirections(direction: Pair<Int, Int>): Char {
    when (direction) {
        Pair(1, 0) -> return 'v'
        Pair(-1, 0) -> return '^'
        Pair(0, -1) -> return '<'
        Pair(0, 1) -> return '>'
    }
    return '0'
}

fun HashMap<Long, Long>.add(key: Long, amount: Long) {
    if (this.containsKey(key)) this[key] = this[key]!! + amount
    else this[key] = amount
}


fun List<IntArray>.check(coords: Pair<Int, Int>): Boolean {
    return coords.first in this.indices && coords.second in this[coords.first].indices
}




