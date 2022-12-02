import arrow.core.flatMap
import java.lang.RuntimeException

fun main(args: Array<String>) {

    val day = args[0].toInt()
    val part = when (args[1].toInt()) {
        1 -> DayPart.ONE
        2 -> DayPart.TWO
        else -> throw RuntimeException("Invalid Part Number")
    }
    val testing = args.size > 2 && args[2] == "test"

    val days = Days()
    days.register(Day01(testing))
    days.register(Day02(testing))

    val line = "======================================="
    println("$line START:  Day $day, part $part")
    val result = days.run(day, part).map { it.toString() }
    println("$line RESULT: $result")
    println("$line END")

}


