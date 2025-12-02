fun main() {
    fun checkAndSum(input: List<String>, sequenceRegex: Regex) = input[0]
        .split(",")
        .flatMap { it.substringBefore('-').toLong()..it.substringAfter('-').toLong() }
        .filter { sequenceRegex.matches(it.toString()) }
        .also { println(it) }
        .sum()

    fun part1(input: List<String>) = checkAndSum(input, """^(\d+)(\1)$""".toRegex())
    fun part2(input: List<String>) = checkAndSum(input, """^(\d+)(\1)+$""".toRegex())

    check(part1(readInput("Day02_test")).also { it.println() } == 1227775554L);
    check(part1(readInput("Day02")).also { it.println() } == 12850231731L);

    check(part2(readInput("Day02_test")).also { it.println() } == 4174379265);
    check(part2(readInput("Day02")).also { it.println() } == 24774350322);
}
