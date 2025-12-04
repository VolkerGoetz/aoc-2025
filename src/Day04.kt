fun main() {

    class RollStorage(lines: List<String>) : Grid<Boolean>(lines, { c ->
        when (c) {
            '@' -> true
            '.' -> false
            else -> throw IllegalArgumentException("Wrong character: $c")
        }
    })

    fun RollStorage.liftableRolls() = allPoints
        .filter { get(it) }
        .filter { getNeighborValues(it).count { it } < 4 }

    fun part1(storage: RollStorage): Int =
        storage.liftableRolls().count()

    fun part2(storage: RollStorage): Int =
        storage.liftableRolls().let { lifts ->
            if (lifts.isEmpty()) {
                0
            } else {
                lifts.forEach { p -> storage.set(p, false) }
                lifts.size + part2(storage)
            }
        }

    check(part1(RollStorage(readInput("Day04_test"))).also { it.println() } == 13)
    check(part1(RollStorage(readInput("Day04"))).also { it.println() } == 1547)

    check(part2(RollStorage(readInput("Day04_test"))).also { it.println() } == 43)
    check(part2(RollStorage(readInput("Day04"))).also { it.println() } == 8948)
}
