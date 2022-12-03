import java.lang.RuntimeException

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

    fun runPart(dayNumber: DayNumber, dayPart: DayPart): SolutionResult {
        val day = days[dayNumber] ?: throw RuntimeException("Day $dayNumber is NOT implemented")
        return when (dayPart) {
            DayPart.ONE -> day.partOne()
            DayPart.TWO -> day.partTwo()
        }
    }

    fun allDays() = days.values
}