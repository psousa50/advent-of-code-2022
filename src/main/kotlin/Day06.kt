class Day06(testing: Boolean = false) : DaySolutions(6, testing) {

    override fun partOne(): SolutionResult = findMarker(4)

    override fun partTwo(): SolutionResult = findMarker(14)

    private fun findMarker(windowSize: Int): String {
        var marker = 0
        val signal = input.first()
        for (i in windowSize until signal.length) {
            marker = i
            if (signal.substring(i - windowSize, i).toSet().size == windowSize) break
        }

        return marker.toString()
    }
}