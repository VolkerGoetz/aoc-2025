fun main() {

    fun solvePart1(previous: String, lastLines: List<String>): Int {

        if (lastLines.isEmpty()) return 0

        val thisLine = lastLines.first()
        val thisLineNew = thisLine.toCharArray()
        var numSplits = 0

        previous.zip(thisLine).forEachIndexed { i, (prev, curr) ->
            if (prev == '|') {
                if (curr == '^') {
                    thisLineNew[i - 1] = '|'
                    thisLineNew[i + 1] = '|'
                    ++numSplits
                } else {
                    thisLineNew[i] = '|'
                }
            }
        }

        return numSplits + solvePart1(thisLineNew.joinToString(""), lastLines.drop(1))
    }

    fun part1(lines: List<String>): Int {
        val firstLine = lines.first().replaceFirst("S", "|")
        return solvePart1(firstLine, lines.drop(1))
    }

    fun solvePart2(previous: List<Long>, lastLines: List<List<Long?>>): List<Long> {
        if (lastLines.isEmpty()) return previous

        val thisLine = lastLines.first()
        val thisLineNew = thisLine.map { it ?: 0 }.toMutableList()

        previous.zip(thisLine).forEachIndexed { i, (prev, curr) ->
            if (curr == null) {
                thisLineNew[i - 1] = thisLineNew[i - 1] + prev
                thisLineNew[i] = 0
                thisLineNew[i + 1] = thisLineNew[i + 1] + prev
            } else {
                thisLineNew[i] = thisLineNew[i] + prev
            }
        }

        return solvePart2(thisLineNew, lastLines.drop(1))
    }

    fun part2(lines: List<String>): Long {
        val quantumManifold = lines
            .map {
                it.map {
                    when (it) {
                        '.' -> 0L
                        '|', 'S' -> 1L
                        '^' -> null
                        else -> throw IllegalArgumentException("Unknown character: $it")
                    }
                }
            }

        val firstLine = quantumManifold.first().map { it ?: 0 }
        return solvePart2(firstLine, quantumManifold.drop(1)).sum()
    }

    check(part1(readInput("Day07_test")).also { it.println() } == 21)
    check(part1(readInput("Day07")).also { it.println() } == 1675)

    check(part2(readInput("Day07_test")).also { it.println() } == 40L)
    check(part2(readInput("Day07")).also { it.println() } > 1497195766L)
}
