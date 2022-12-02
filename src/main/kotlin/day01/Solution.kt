package day01

import Day
import Solution
import SolutionInput
import arrow.core.right

class SolutionPart1() : Solution(1, DayPart.ONE) {
    override fun run(input: SolutionInput) = getElvesCalories(input).max().right()
}

class SolutionPart2() : Solution(1, DayPart.TWO) {
    override fun run(input: SolutionInput) = getElvesCalories(input).sortedDescending().take(3).sum().right()
}

class Day01() : Day(
    SolutionPart1(),
    SolutionPart2(),
)

private fun getElvesCalories(input: SolutionInput): List<Int> =
    input.fold(listOf(0)) { sums, value ->
        when {
            value.isEmpty() -> sums + 0
            else -> sums.dropLast(1) + (sums.last() + value.toInt())
        }
    }

