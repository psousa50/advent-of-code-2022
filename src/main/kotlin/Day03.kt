import arrow.core.right

class Day03(testing: Boolean = false) : DaySolutions(3, testing) {
    override fun partOne(): SolutionResult {
        val ruckSacks = input.map { RuckSack(it) }
        return ruckSacks.map { it.commonItem() }.sumOf { itemPriority(it) }.right()
    }

    override fun partTwo(): SolutionResult {
        val ruckSacks = input.map { RuckSack(it) }
        return ruckSacks.chunked(3).map { findBadge(it) }.sumOf { itemPriority(it) }.right()
    }

    private fun findBadge(ruckSacks: List<RuckSack>): Char =
        ruckSacks[0].items.first { ruckSacks[1].items.contains(it) && ruckSacks[2].items.contains(it) }

    private fun itemPriority(item: Char): Int =
        item.code + 1 - if (item.isLowerCase()) 'a'.code else ('A'.code - 26)

    private class RuckSack(items: String) {
        val compartment1: String
        val compartment2: String
        val items get() = compartment1 + compartment2

        init {
            val l = items.length
            compartment1 = items.substring(0 until l / 2)
            compartment2 = items.substring(l / 2 until l)
        }

        fun commonItem(): Char =
            compartment1.first { compartment2.contains(it) }
    }
}