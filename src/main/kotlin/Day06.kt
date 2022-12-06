class Day06(testing: Boolean = false) : DaySolutions(6, testing) {

    override fun partOne(): SolutionResult = findMarker(4)

    override fun partTwo(): SolutionResult = findMarker(14)

    private fun findMarker(windowSize: Int): String {
        var window = ""
        var marker = 0
        for (c in input.first()) {
            marker += 1
            window += c
            if (window.length > windowSize) window = window.substring(1)
            if (window.toSet().size == windowSize) break
        }

        return marker.toString()
    }
}