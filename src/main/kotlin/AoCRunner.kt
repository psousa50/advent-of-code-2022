import arrow.core.Either
import arrow.core.flatMap

class AoCRunner(testing: Boolean = false) {
    private val days: MutableMap<Int, DaySolutions> = mutableMapOf()

    init {
        register(Day01(testing))
        register(Day02(testing))
        register(Day03(testing))
    }

    private fun register(day: DaySolutions) {
        days[day.dayNumber] = day
    }

    fun runPart(dayNumber: DayNumber, dayPart: DayPart): SolutionResult =
        Either.fromNullable(days[dayNumber])
            .flatMap {
                when (dayPart) {
                    DayPart.ONE -> it.partOne()
                    DayPart.TWO -> it.partTwo()
                }
            }.mapLeft { "Day $dayNumber is NOT implemented" }

    fun allDays() = days.values.sortedBy { it.dayNumber }
}