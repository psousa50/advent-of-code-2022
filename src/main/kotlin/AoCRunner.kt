import arrow.core.*
import java.io.File

enum class DayPart {
    ONE,
    TWO
}

typealias DayNumber = Int
typealias SolutionInput = List<String>
typealias SolutionResult = Either<String, Int>

open class Solution(
    val dayNumber: DayNumber,
    val dayPart: DayPart,
) {
    open fun run(input: SolutionInput): SolutionResult = "Part $dayPart of day $dayNumber is NOT implemented".left()
}

open class Day(
    val part1: Solution,
    val part2: Solution,
) {
    val number get() = part1.dayNumber
}

class AoCRunner() {
    private val days: MutableMap<Int, Day> = mutableMapOf()

    init {
        register(day01.Day01())
        register(day02.Day02())
    }

    private fun register(day: Day) {
        days[day.number] = day
    }

    fun showAllResults(testing: Boolean = false) {
        for (day in days.values) showResult(day, testing)
    }

    private fun showResult(day: Day, testing: Boolean) {
        val input = getInputFile(day.number, testing).readLines()
        showResult(day.part1, input)
        showResult(day.part2, input)
    }

    fun showResult(dayNumber: DayNumber, dayPart: DayPart, testing: Boolean = false) {
        daySolutionPart(dayNumber, dayPart).fold(
            {
                println(it)
            },
            {
                val input = getInputFile(dayNumber, testing).readLines()
                showResult(it, input)
            }
        )
    }

    fun runPart(dayNumber: DayNumber, dayPart: DayPart, testing: Boolean = false) : SolutionResult {
        val input = getInputFile(dayNumber, testing).readLines()
        return daySolutionPart(dayNumber, dayPart).flatMap { it.run(input) }
    }

    private fun daySolutionPart(dayNumber: DayNumber, dayPart: DayPart): Either<String, Solution> =
        Either.fromNullable(days[dayNumber])
            .map {
                when (dayPart) {
                    DayPart.ONE -> it.part1
                    DayPart.TWO -> it.part2
                }
            }.mapLeft { "Day $dayNumber is NOT implemented" }

    private fun filePathToResources(): String {
        return "src/main/resources"
    }

    private fun getInputFile(dayNumber: DayNumber, testing: Boolean): File {
        val suffix = if (testing) "_test" else ""
        val filename = "DAY_${"%02d".format(dayNumber)}${suffix}.txt"
        return File("${filePathToResources()}/inputs/$filename")
    }

    private fun showResult(solution: Solution, input: SolutionInput) {
        val line = "======================================="
        println("$line START:  Day ${solution.dayNumber}, part ${solution.dayPart}")
        val result = solution.run(input).fold({ it }, { it.toString() })
        println("$line RESULT: $result")
        println("$line END")
    }
}
