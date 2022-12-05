class Day01(testing: Boolean = false) : DaySolutions(1, testing) {
    override fun partOne() =
        elvesCalories(input).max().toString()

    override fun partTwo(): SolutionResult =
        elvesCalories(input).sortedDescending().take(3).sum().toString()

    private fun elvesCalories(input: SolutionInput): List<Int> =
        input.fold(listOf(0)) { sums, value ->
            when {
                value.isEmpty() -> sums + 0
                else -> sums.dropLast(1) + (sums.last() + value.toInt())
            }
        }.filter { it > 0 }
}
