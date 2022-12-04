class Day04(testing: Boolean = false) : DaySolutions(4, testing) {
    override fun partOne(): SolutionResult =
        input
            .map { parse(it) }
            .map { oneContainsTheOther(it) }
            .sumOf { it.toInt() }

    private fun oneContainsTheOther(assignment: Pair<Interval, Interval>): Boolean =
        assignment.first.contains(assignment.second) || assignment.second.contains(assignment.first)

    private fun contains(first: Interval, second: Interval): Boolean =
        first.lower <= second.lower && first.upper >= second.upper


    private fun parse(assignment: String): Pair<Interval, Interval> {
        return assignment
            .split(",")
            .map { Interval(it) }
            .toPair()
    }

    class Interval(interval: String) {
        val lower: Int
        val upper: Int

        init {
            val p = interval.split("-")
            lower = p[0].toInt()
            upper = p[1].toInt()
        }

        fun contains(theOther: Interval) =
            this.lower <= theOther.lower && this.upper >= theOther.upper

    }
}

fun Boolean.toInt() = if (this) 1 else 0

fun <T> List<T>.toPair() = Pair(this.first(), this.last())