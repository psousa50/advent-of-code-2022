import java.lang.RuntimeException

fun main(args: Array<String>) {

    val day = args[0].toInt()
    val part = when (args[1].toInt()) {
        1 -> DayPart.ONE
        2 -> DayPart.TWO
        else -> throw RuntimeException("Invalid Part Number")
    }

    val days = Days()
    days.register(Day1())

    println("Starting day $day, part $part")
    val result: String = days.run(day, part)
    println(result)

}


