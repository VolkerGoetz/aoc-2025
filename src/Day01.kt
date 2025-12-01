import kotlin.math.abs

fun main() {
    fun List<String>.toIntList() =
        map { s -> s.drop(1).toInt() * (if (s[0] == 'R') 1 else -1) }

    fun part1(input: List<String>, start: Int = 50): Int {
        var current = start
        var numZeros = 0
        for (dial in input.toIntList()) {
            current = (current + dial).mod(100)
            if (current == 0) {
                numZeros++
            }
        }

        return numZeros
    }

    fun part2(input: List<String>, start: Int = 50): Int {
        var current = start
        var numZeros = 0
        for (dial in input.toIntList()) {
            val fullRotations = abs(dial / 100)
            numZeros += fullRotations

            val partialRotations = dial % 100
            if (current != 0) {
                if (partialRotations > 0) {
                    // turn right
                    if (partialRotations >= 100 - current) {
                        numZeros++
                    }
                } else {
                    // turn left
                    if (partialRotations <= -current) {
                        numZeros++
                    }
                }
            }

            current = (current + dial).mod(100)
        }

        return numZeros
    }


    //part1(listOf("L1"), 0)
    //part1(listOf("R1"), 0)
    //
    //part1(listOf("R8", "L19"), 11)
    //
    //part1(listOf("L10"), 5)

    check(part1(readInput("Day01_test")) == 3)
    check(part1(readInput("Day01")).also { it.println() } == 997)

    //part2(listOf("L68"))
    //part2(listOf("L168"))
    check(part2(readInput("Day01_test")) == 6)
    check(part2(readInput("Day01")).also { it.println() } == 5978)
}
