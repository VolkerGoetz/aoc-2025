import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

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

data class Point(val x: Int, val y: Int) : Comparable<Point> {
    override fun compareTo(other: Point) =
        when {
            this.y == other.y -> this.x.compareTo(other.x)
            else -> this.y.compareTo(other.y)
        } //.also { println("$this to $other -> $it") }

    override fun toString() = "$x/$y"
}

data class Direction(val dx: Int, val dy: Int) {
    companion object {
        val North = Direction(0, -1)
        val East = Direction(1, 0)
        val South = Direction(0, 1)
        val West = Direction(-1, 0)
        val NorthEast = North + East
        val SouthEast = South + East
        val SouthWest = South + West
        val NorthWest = North + West
        val MainDirections = listOf(North, East, South, West)
        val AllDirections = listOf(North, NorthEast, East, SouthEast, South, SouthWest, West, NorthWest)
    }

    override fun toString() = "$dx/$dy"
}

infix fun Int.to(that: Int): Point = Point(this, that)
fun Pair<Point, Point>.vector(): Point =
    Point(second.x - first.x, second.y - first.y)

operator fun Direction.plus(that: Direction): Direction =
    Direction(this.dx + that.dx, this.dy + that.dy)

operator fun Point.plus(that: Point): Point =
    Point(this.x + that.x, this.y + that.y)

operator fun Point.plus(that: Direction): Point =
    Point(this.x + that.dx, this.y + that.dy)

operator fun Point.times(that: Int): Point =
    Point(this.x * that, this.y * that)

fun Point.manhattanDistTo(other: Point): Int =
    abs(x - other.x) + abs(y - other.y)

data class Point3D(val x: Int, val y: Int, val z: Int) {

    override fun toString() = "Point3D($x/$y/$z)"
}

fun String.toPoint3D() =
    split(',').map { it.toInt() }.let { Point3D(it[0], it[1], it[2]) }


fun Point3D.distanceTo(other: Point3D) =
    sqrt((x - other.x).toDouble().pow(2) + (y - other.y).toDouble().pow(2) + (z - other.z).toDouble().pow(2))

open class Grid<T>(lines: List<String>, producer: (Char) -> T) : ArrayList<ArrayList<T>>() {
    val gridSize: Point

    init {
        for (x in lines[0].indices) {
            var col = ArrayList<T>()
            for (y in lines.indices) {
                col.add(producer(lines[y][x]))
            }
            add(col)
        }
        gridSize = lines[0].length to (lines.size)
    }

    val allPoints by lazy {
        indices.flatMap { x ->
            this[x].indices.map { y -> Point(x, y) }
        }
    }

    operator fun get(pos: Point) = this[pos.x][pos.y]
    operator fun set(pos: Point, value: T) {
        this[pos.x][pos.y] = value
    }

    fun getOrNull(pos: Point) = if (pos in this) get(pos) else null

    operator fun contains(p: Point) =
        p.x in this.indices && p.y in this[0].indices

    fun getNeighbors(point: Point, directions: Collection<Direction> = Direction.AllDirections) =
        directions.map { point + it }

    fun getNeighborValues(point: Point, directions: Collection<Direction> = Direction.AllDirections) =
        getNeighbors(point, directions)
            .mapNotNull { getOrNull(it) }
}

class IntGrid(lines: List<String>) : Grid<Int>(lines, Char::digitToInt)
class CharGrid(lines: List<String>) : Grid<Char>(lines, { c: Char -> c })

fun CharGrid.printGrid() {
    for (y in 0..<gridSize.y) {
        for (x in 0..<gridSize.x) {
            print(get(x to y))
        }
        kotlin.io.println()
    }
}
