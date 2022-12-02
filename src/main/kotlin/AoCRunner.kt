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
        register(Day01())
        register(Day02())
    }

    fun register(day: Day) {
        days[day.number] = day
    }

    fun runAll(testing: Boolean = false) {
        for (day in days.values) run(day, testing)
    }

    fun run(day: Day, testing: Boolean) {
        val input = getInputFile(day, testing).readLines()
        showResult(day.part1, input)
        showResult(day.part2, input)
    }

    fun runPart(dayNumber: DayNumber, dayPart: DayPart, testing: Boolean = false): SolutionResult {
        return Either.fromNullable(days[dayNumber])
            .flatMap {
                val input = getInputFile(it, testing).readLines()
                when (dayPart) {
                    DayPart.ONE -> it.part1.run(input)
                    DayPart.TWO -> it.part2.run(input)
                }
            }.mapLeft { "Day $dayNumber is NOT implemented" }
    }

    private fun filePathToResources(): String {
        return "src/main/resources"
    }

    private fun getInputFile(day: Day, testing: Boolean): File {
        val suffix = if (testing) "_test" else ""
        val filename = "DAY_${"%02d".format(day.number)}${suffix}.txt"
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
