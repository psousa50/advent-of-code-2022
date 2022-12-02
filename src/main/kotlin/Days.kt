import arrow.core.*
import java.io.File

enum class DayPart {
    ONE,
    TWO
}

typealias DayNumber = Int
typealias SolutionReturnType = Either<String, Int>

class Days() {
    private val days: MutableMap<Int, Day> = mutableMapOf()

    fun register(day: Day) {
        days[day.number] = day
    }

    fun run(dayNumber: DayNumber, dayPart: DayPart): SolutionReturnType {
        return Either.fromNullable(days[dayNumber])
            .flatMap {
                when (dayPart) {
                    DayPart.ONE -> it.part1()
                    DayPart.TWO -> it.part2()
                }
            }.mapLeft { "Day $dayNumber is NOT implemented" }
    }
}

open class Day(val number: DayNumber, private val testing: Boolean = false) {
    open fun part1(): SolutionReturnType {
        return "Part ${DayPart.ONE} of day $number is NOT implemented".left()
    }

    open fun part2(): SolutionReturnType {
        return "Part ${DayPart.TWO}  of day $number is NOT implemented".left()
    }

    private fun filePathToResources(): String {
        return "src/main/resources"
    }

    fun getInputFile(part: DayPart): File {
        val suffix = if (testing) "_test" else ""
        val filename = "DAY_${"%02d".format(number)}_PART_${part}${suffix}.txt"
        return File("${filePathToResources()}/inputs/$filename")
    }
}
