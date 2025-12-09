fun main() {
    fun List<String>.toPoints3D() = map { it.toPoint3D() }

    fun List<Point3D>.toConnectionsWithDistance(): List<Triple<Point3D, Point3D, Double>> =
        buildList {
            for (i in this@toConnectionsWithDistance.indices) {
                val f = this@toConnectionsWithDistance[i]
                for (j in i + 1..this@toConnectionsWithDistance.lastIndex) {
                    val s = this@toConnectionsWithDistance[j]
                    add(Triple(f, s, f.distanceTo(s)))
                }
            }
        }.sortedBy { it.third }

    fun part1(lines: List<String>, numConnectionsToCheck: Int = 1000): Int {
        val points = lines.toPoints3D()
        val connectionsWithDistance = points.toConnectionsWithDistance()
        val circuits = points.map { mutableSetOf(it) }.toMutableList()

        connectionsWithDistance
            .take(numConnectionsToCheck)
            .forEach { conn ->
                val set1 = circuits.firstOrNull { conn.first in it }
                val set2 = circuits.firstOrNull { conn.second in it }

                when {
                    set1 == null && set2 != null -> set2.add(conn.first)
                    set1 != null && set2 == null -> set1.add(conn.second)
                    set1 != null && set2 != null && set1 != set2 -> {
                        set1.addAll(set2)
                        circuits.remove(set2)
                    }
                }
            }

        return circuits.map { it.size }.sortedDescending().take(3).reduce(Int::times)
    }

    fun part2(lines: List<String>): Long {
        val points = lines.toPoints3D()
        val connectionsWithDistance = points.toConnectionsWithDistance()
        val circuits = points.map { mutableSetOf(it) }.toMutableList()

        for (conn in connectionsWithDistance) {
            val set1 = circuits.firstOrNull { conn.first in it }
            val set2 = circuits.firstOrNull { conn.second in it }

            when {
                set1 == null && set2 != null -> set2.add(conn.first)
                set1 != null && set2 == null -> set1.add(conn.second)
                set1 != null && set2 != null && set1 != set2 -> {
                    set1.addAll(set2)
                    circuits.remove(set2)
                }
            }
            if (circuits.size == 1)
                return conn.first.x.toLong() * conn.second.x.toLong()
        }
        return -1
    }

    check(part1(readInput("Day08_test"), 10).also { it.println() } == 40)
    check(part1(readInput("Day08")).also { it.println() } == 171503)

    check(part2(readInput("Day08_test")).also { it.println() } == 25272L)
    check(part2(readInput("Day08")).also { it.println() } == 9069509600L)
}
