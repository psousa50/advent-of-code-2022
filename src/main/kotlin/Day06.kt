class Day06(testing: Boolean = false) : DaySolutions(6, testing) {

    override fun partOne(): SolutionResult {

        var window = ""
        var marker = 0
        for (c in input.first()) {
            marker += 1
            window += c
            if (window.length > 4) window = window.substring(1)
            if (window.toSet().size == 4) break
        }

        return marker.toString()
    }
}