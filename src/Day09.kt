import kotlin.math.abs

fun main() {
    fun List<String>.toPoints() = map { it.toPoint() }

    fun Pair<Point, Point>.sorted() =
        if (this.first <= this.second)
            Pair(this.first, this.second)
        else
            Pair(this.second, this.first)

    fun List<Point>.toRectangles() =
        buildList {
            for (i in this@toRectangles.indices) {
                val f = this@toRectangles[i]
                for (j in i + 1..this@toRectangles.lastIndex) {
                    val s = this@toRectangles[j]
                    add(Pair(f, s).sorted())
                }
            }
        }

    fun part1(lines: List<String>) =
        lines.toPoints()
            .toRectangles()
            .map { (first, second) -> (abs(first.x - second.x) + 1).toLong() * (abs(first.y - second.y) + 1).toLong() }
            .max()

    check(part1(readInput("Day09_test")).also { it.println() } == 50L)
    check(part1(readInput("Day09")).also { it.println() } == 4748985168L)
}
