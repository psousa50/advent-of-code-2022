class Day01(testing: Boolean = false) : DaySolutions(1, testing) {
    override fun partOne() =
        elvesCalories(input).max().toString()

    override fun partTwo(): SolutionResult =
        elvesCalories(input).sortedDescending().take(3).sum().toString()

    private fun elvesCalories(input: SolutionInput): List<Int> =
        input.splitByBlankLine { g -> g.map { l -> l.sumOf { it.toInt() } } }
}
