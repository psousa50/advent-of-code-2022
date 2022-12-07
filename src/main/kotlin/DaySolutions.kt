import java.io.File
open class DaySolutions(val dayNumber: DayNumber, private val testing: Boolean = false) {
    val input: SolutionInput

    init {
        input = getInputFile().readLines()
    }

    open fun partOne(): SolutionResult {
        throw NotImplementedError("Part ${DayPart.ONE} of day $dayNumber is NOT implemented")
    }

    open fun partTwo(): SolutionResult {
        throw NotImplementedError("Part ${DayPart.TWO} of day $dayNumber is NOT implemented")
    }

    fun part(dayPart: DayPart) =
        when (dayPart) {
            DayPart.ONE -> partOne()
            DayPart.TWO -> partTwo()
        }

    private fun filePathToResources(): String {
        return "src/main/resources"
    }

    private fun getInputFile(): File {
        val suffix = if (testing) "_test" else ""
        val filename = "DAY_${"%02d".format(dayNumber)}${suffix}.txt"
        return File("${filePathToResources()}/inputs/$filename")
    }
}
