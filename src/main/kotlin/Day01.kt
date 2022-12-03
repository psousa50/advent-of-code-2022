import arrow.core.right

class Day01(testing: Boolean = false) : DaySolutions(1, testing) {
    override fun partOne(): SolutionResult =
        getElvesCalories(input).max()

    override fun partTwo(): SolutionResult =
        getElvesCalories(input).sortedDescending().take(3).sum()

    private fun getElvesCalories(input: SolutionInput): List<Int> =
        input.fold(listOf(0)) { sums, value ->
            when {
                value.isEmpty() -> sums + 0
                else -> sums.dropLast(1) + (sums.last() + value.toInt())
            }
        }
}
