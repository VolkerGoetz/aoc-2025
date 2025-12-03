import kotlin.time.measureTime

fun main() {

    fun calculateJoltageForBank_LONG(bank: String, numBatteries: Int = 2): Long {
        var availableBatteries = bank.toList().map { v -> Integer.valueOf(v.toString()).toLong() }
        return (numBatteries downTo 1).fold(0L) { joltage, n ->
            val batVal = availableBatteries.dropLast(n - 1).max()
            availableBatteries = availableBatteries.dropWhile { it != batVal }.drop(1)
            joltage * 10L + batVal
        }
    }

    fun calculateJoltageForBank_STR(bank: String, numBatteries: Int = 2): Long {
        var availableBatteries = bank
        return (numBatteries - 1 downTo 0).fold("") { joltage, toDrop ->
            val batVal = availableBatteries.dropLast(toDrop).max()
            availableBatteries = availableBatteries.dropWhile { it != batVal }.drop(1)
            joltage + batVal
        }.toLong()
    }

    fun calculateJoltageForBank(bank: String, numBatteries: Int): String {
        return if (numBatteries == 0) {
            ""
        } else {
            val batVal = bank.dropLast(numBatteries - 1).max()
            batVal + calculateJoltageForBank(bank.drop(bank.indexOf(batVal) + 1), numBatteries - 1)
        }
    }

    fun calculateJoltage(input: List<String>, numBatteries: Int = 2): Long =
        input.sumOf { calculateJoltageForBank(it, numBatteries).toLong() }

    fun part1(input: List<String>): Long = calculateJoltage(input, 2)
    fun part2(input: List<String>): Long = calculateJoltage(input, 12)

    measureTime {
        check(part1(listOf("12345")).also { println(it) } == 45L)
        check(part1(listOf("212")).also { println(it) } == 22L)
        check(part1(listOf("987654321111111")).also { println(it) } == 98L)
        check(part1(listOf("811111111111119")).also { println(it) } == 89L)
        check(part1(listOf("234234234234278")).also { println(it) } == 78L)
        check(part1(listOf("818181911112111")).also { println(it) } == 92L)

        check(part1(readInput("Day03_test")).also { it.println() } == 357L)
        check(part1(readInput("Day03")).also { it.println() } == 17301L)


        check(part2(listOf("987654321111111")).also { println(it) } == 987654321111L)
        check(part2(listOf("811111111111119")).also { println(it) } == 811111111119L)
        check(part2(listOf("234234234234278")).also { println(it) } == 434234234278L)
        check(part2(listOf("818181911112111")).also { println(it) } == 888911112111L)

        check(part2(readInput("Day03_test")).also { it.println() } == 3121910778619L)
        check(part2(readInput("Day03")).also { it.println() } == 172162399742349L)
    }.also { println(it) }
}
