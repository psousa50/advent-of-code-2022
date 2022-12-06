class Day06(testing: Boolean = false) : DaySolutions(6, testing) {

    override fun partOne(): SolutionResult = findMarker(4)

    override fun partTwo(): SolutionResult = findMarker(14)

    private fun findMarker(windowSize: Int): String =
        input.first()
            .windowedSequence(windowSize)
            .map { it.toSet() }
            .takeWhile { it.size != windowSize }
            .count()
            .plus(windowSize)
            .toString()
}