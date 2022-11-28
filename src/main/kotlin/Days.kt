enum class DayPart {
    ONE,
    TWO
}

const val NUMBER_OF_DAYS = 25

typealias DayNumber = Int
typealias SolutionReturnType = String

class Days {
    private val days: MutableList<Day> = mutableListOf()

    fun register(day: Day) {
        if (days.none { it.number == day.number })
            days.add(day)
    }

    fun run(dayNumber: DayNumber, dayPart: DayPart): SolutionReturnType {
        return days.firstOrNull {it.number == dayNumber}?.let {
            when (dayPart) {
                DayPart.ONE -> it.part1()
                DayPart.TWO -> it.part2()
            }
        } ?: "Day $dayNumber is NOT implemented"
    }
}

open class Day(val number: DayNumber) {
    open fun part1(): SolutionReturnType {
        return "Part ${DayPart.ONE} of day $number is NOT implemented"
    }

    open fun part2(): SolutionReturnType {
        return "Part ${DayPart.TWO}  of day $number is NOT implemented"
    }
}
