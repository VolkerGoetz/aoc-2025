import kotlin.math.max
import kotlin.math.min

fun main() {

    class IngredientRange(val from: Long, val to: Long) {
        val size = to - from + 1

        constructor(range: String) : this(range.substringBefore('-').toLong(), range.substringAfter('-').toLong())

        override fun toString(): String = "($from - $to)"

        operator fun contains(id: Long) = id in from..to
        operator fun contains(other: IngredientRange) = from <= other.from && to >= other.to

        fun intersects(other: IngredientRange) = other.from in this || other.to in this || this.from in other || this.to in other
        fun mergeWith(other: IngredientRange) = if (other.intersects(this)) IngredientRange(min(from, other.from), max(to, other.to)) else null
    }

    operator fun List<IngredientRange>.contains(id: Long) = any { id in it }
    operator fun List<IngredientRange>.contains(other: IngredientRange) = any { other in it }

    fun getIngredientRanges(lines: List<String>) =
        lines.takeWhile { !it.isEmpty() }
            .map { IngredientRange(it) }

    fun getIngredientIds(lines: List<String>) =
        lines.takeLastWhile { !it.isEmpty() }
            .map { it.toLong() }

    fun part1(lines: List<String>) =
        getIngredientRanges(lines).let { ranges ->
            getIngredientIds(lines).count { it in ranges }
        }

    fun part2(lines: List<String>): Long {
        val ranges = getIngredientRanges(lines).toMutableList()
        val consolidated = mutableListOf<IngredientRange>()

        while (ranges.isNotEmpty()) {
            val range = ranges.removeFirst()
            var consumed = false
            for (conRange in consolidated) {
                conRange.mergeWith(range)?.let {
                    ranges.add(it)
                    consolidated.remove(conRange)
                    consumed = true
                    break
                }
            }
            if (!consumed) {
                consolidated.add(range)
            }
        }

        return consolidated.sumOf { it.size }
    }

    check(part1(readInput("Day05_test")).also { it.println() } == 3)
    check(part1(readInput("Day05")).also { it.println() } == 775)

    check(part2(readInput("Day05_test")).also { it.println() } == 14L)
    check(part2(readInput("Day05")).also { it.println() } == 350684792662845L)
}
