import java.lang.RuntimeException

fun main(args: Array<String>) {


    val runAll = args[0] == "ALL"

    if (runAll) {
        val aoCRunner = AoCRunner()
        for (day in aoCRunner.allDays().sortedBy { it.dayNumber }) {
            showResult(aoCRunner, day.dayNumber, DayPart.ONE)
            showResult(aoCRunner, day.dayNumber, DayPart.TWO)
        }
    } else {
        val day = args[0].toInt()
        val part = when (args[1].toInt()) {
            1 -> DayPart.ONE
            2 -> DayPart.TWO
            else -> throw RuntimeException("Invalid Part Number")
        }
        val testing = args.size > 2 && args[2] == "test"

        val aoCRunner = AoCRunner(testing)

        showResult(aoCRunner, day, part)
    }
}

fun showResult(aoCRunner: AoCRunner, dayNumber: DayNumber, dayPart: DayPart) {
    val result = aoCRunner.runPart(dayNumber, dayPart)
    val ident = " > "
    println("$ident START:  Day $dayNumber, part $dayPart")
    println("$ident RESULT: $result")
    println("$ident END")
}

