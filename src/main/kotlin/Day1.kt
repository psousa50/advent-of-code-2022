class Day1(testing: Boolean = false) : Day(1, testing) {

    override fun part1(): SolutionReturnType {
        val sums = getInputFile(DayPart.ONE).useLines {lines->
            lines.fold(listOf(0)) {sums, value ->
                when {
                    value.isEmpty() -> sums + 0
                    else -> sums.dropLast(1) + (sums.last() + value.toInt())
                }
            }
        }

        return sums.max().toString()
    }
}


