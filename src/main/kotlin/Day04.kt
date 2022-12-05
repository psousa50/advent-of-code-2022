class Day04(testing: Boolean = false) : DaySolutions(4, testing) {
    override fun partOne(): SolutionResult =
        countAssignmentsInteractions(input) { containsOrIsContainedBy(it) }.toString()

    override fun partTwo(): SolutionResult =
        countAssignmentsInteractions(input) { intercepts(it) }.toString()

    private fun countAssignmentsInteractions(
        input: SolutionInput,
        conditionIsMet: (assignmentPair: AssignmentPair) -> Boolean
    ) = input
        .map { parseAssignmentPair(it) }
        .map { conditionIsMet(it) }
        .count { it }

    private fun containsOrIsContainedBy(assignmentPair: AssignmentPair): Boolean =
        assignmentPair.first.contains(assignmentPair.second) || assignmentPair.second.contains(assignmentPair.first)

    private fun intercepts(assignmentPair: AssignmentPair): Boolean =
        assignmentPair.first.intercepts(assignmentPair.second)

    private fun parseAssignmentPair(assignment: String): AssignmentPair {
        return assignment
            .split(",")
            .map { parseAssignment(it) }
            .toPair()
    }

    private fun parseAssignment(assignment: String): Assignment {
        val p = assignment.split("-")
        val lower = p[0].toInt()
        val upper = p[1].toInt()

        return Assignment(lower, upper)
    }

    data class Assignment(
        private val lower: Int,
        private val upper: Int
    ) {
        fun contains(theOther: Assignment) =
            this.lower <= theOther.lower && this.upper >= theOther.upper

        fun intercepts(theOther: Assignment) =
            this.upper >= theOther.lower && this.lower <= theOther.upper

        override fun toString(): String = "[$lower-$upper]"
    }
}

fun <T> List<T>.toPair() = Pair(this[0], this[1])

typealias AssignmentPair = Pair<Day04.Assignment, Day04.Assignment>