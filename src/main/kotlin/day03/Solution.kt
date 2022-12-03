package day03

import Day
import Solution
import SolutionInput
import SolutionResult
import arrow.core.right

private class SolutionPartOne : Solution(3, DayPart.ONE) {
    override fun run(input: SolutionInput): SolutionResult {
        val ruckSacks = input.map { RuckSack(it) }
        return ruckSacks.map { it.commonItem() }.sumOf { itemPriority(it) }.right()
    }
}

private class SolutionPartTwo : Solution(3, DayPart.TWO) {
    override fun run(input: SolutionInput): SolutionResult {
        val ruckSacks = input.map { RuckSack(it) }
        return ruckSacks.chunked(3).map { findBadge(it) }.sumOf { itemPriority(it) }.right()
    }

    private fun findBadge(ruckSacks: List<RuckSack>): Char =
        ruckSacks[0].items.first { ruckSacks[1].items.contains(it) && ruckSacks[2].items.contains(it) }
}

class Day03 : Day(
    SolutionPartOne(),
    SolutionPartTwo(),
)

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