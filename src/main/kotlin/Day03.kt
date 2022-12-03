import arrow.core.right

class Day03(testing: Boolean = false) : DaySolutions(3, testing) {
    override fun partOne(): SolutionResult =
        input.map { commonItem(it) }.sumOf { itemPriority(it) }

    override fun partTwo(): SolutionResult =
        input.chunked(3).map { findBadge(it) }.sumOf { itemPriority(it) }

    private fun itemPriority(item: Char): Int =
        item.code + 1 - if (item.isLowerCase()) 'a'.code else ('A'.code - 26)

    private fun commonItem(items: String): Char {
        val middle = items.length / 2
        val compartment1 = items.substring(0 until middle)
        val compartment2 = items.substring(middle)
        return compartment1.first { compartment2.contains(it) }
    }

    private fun findBadge(ruckSacks: List<String>): Char =
        ruckSacks[0].first { ruckSacks[1].contains(it) && ruckSacks[2].contains(it) }
}