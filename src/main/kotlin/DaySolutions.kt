import arrow.core.left
import java.io.File
import java.lang.RuntimeException

open class DaySolutions(val dayNumber: DayNumber, private val testing: Boolean = false) {
    val input: SolutionInput

    init {
        input = getInputFile().readLines()
    }

    open fun partOne(): SolutionResult {
        throw RuntimeException("Part ${DayPart.ONE} of day $dayNumber is NOT implemented")
    }

    open fun partTwo(): SolutionResult {
        throw RuntimeException("Part ${DayPart.TWO} of day $dayNumber is NOT implemented")
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