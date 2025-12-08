import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {

    fun readInputUntrimmed(name: String) =
        Path("src/$name.txt")
            .readText()
            .lines()
            .filter { it.isNotBlank() }

    fun String.toOperator(): (Long, Long) -> Long =
        when (this) {
            "+" -> Long::plus
            "*" -> Long::times
            else -> throw IllegalArgumentException("Unknown operator: $this")
        }

    fun Char.toOperator(): (Long, Long) -> Long =
        when (this) {
            '+' -> Long::plus
            '*' -> Long::times
            else -> throw IllegalArgumentException("Unknown operator: $this")
        }

    fun List<String>.parseToProblemPart1() =
        map { it.trim().split("\\s+".toRegex()) }
            .let {
                val numRows = it.size
                val numCols = it.first().size
                buildList {
                    for (c in 0..<numCols) {
                        add(
                            buildList {
                                for (r in 0..<numRows - 1) {
                                    add(it.get(r).get(c).toLong())
                                }
                            } to it.last().get(c).toOperator()
                        )
                    }
                }
            }

    fun part1(lines: List<String>) =
        lines.parseToProblemPart1().sumOf { (row, op) -> row.reduce(op) }

    fun part2(lines: List<String>): Long {
        var sum = 0L
        val operands = mutableListOf<Long>()
        lines.first().indices.reversed().forEach { col ->
            lines.dropLast(1).map {
                it.get(col)
            }.joinToString("").trim()
                .toLongOrNull()?.let {
                    operands.add(it)
                }
            val op = lines.last().get(col)
            if (!op.isWhitespace()) {
                sum += operands.reduce(op.toOperator())
                operands.clear()
            }
        }

        return sum
    }

    //check(part1(listOf("123", "45", "6", "*")).also { it.println() } == 33210L)
    //check(part1(listOf("328", "64", "98", "+")).also { it.println() } == 490L)
    check(part1(readInput("Day06_test")).also { it.println() } == 4277556L)
    check(part1(readInput("Day06")).also { it.println() } == 5552221122013L)

    check(part2(readInputUntrimmed("Day06_test")).also { it.println() } == 3263827L)
    check(part2(readInputUntrimmed("Day06")).also { it.println() } == 11371597126232L)
}
