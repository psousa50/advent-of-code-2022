class Day01(testing: Boolean = false) : DaySolutions(1, testing) {
    override fun partOne() =
        elvesCalories(input).max().bind()

    override fun partTwo(): SolutionResult =
        elvesCalories(input).sortedDescending().take(3).sum().bind()

    private fun elvesCalories(input: SolutionInput): List<Int> =
        input.splitByBlankLine { g -> g.map { l -> l.sumOf { it.toInt() } } }
}
