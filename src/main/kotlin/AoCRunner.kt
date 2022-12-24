class AoCRunner(testing: Boolean = false) {
    private val days: MutableMap<Int, DaySolutions> = mutableMapOf()

    init {
        register(Day01(testing))
        register(Day02(testing))
        register(Day03(testing))
        register(Day04(testing))
        register(Day05(testing))
        register(Day06(testing))
        register(Day07(testing))
        register(Day08(testing))
        register(Day09(testing))
        register(Day10(testing))
        register(Day11(testing))
        register(Day12(testing))
        register(Day13(testing))
        register(Day14(testing))
        register(Day15(testing))
    }

    private fun register(day: DaySolutions) {
        days[day.dayNumber] = day
    }

    fun runPart(dayNumber: DayNumber, dayPart: DayPart): SolutionResult =
        days[dayNumber]?.part(dayPart) ?: throw NotImplementedError("Day $dayNumber is NOT implemented")

    fun allDays() = days.values
}