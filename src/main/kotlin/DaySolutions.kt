import arrow.core.left
import java.io.File

open class DaySolutions(val dayNumber: DayNumber, private val testing: Boolean = false) {
    val input: SolutionInput

    init {
        input = getInputFile().readLines()
    }

    open fun partOne(): SolutionResult {
        return "Part ${DayPart.ONE} of day $dayNumber is NOT implemented".left()
    }

    open fun partTwo(): SolutionResult {
        return "Part ${DayPart.ONE} of day $dayNumber is NOT implemented".left()
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