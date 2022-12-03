import java.lang.RuntimeException

fun main(args: Array<String>) {


    val runAll = args[0] == "ALL"

    if (runAll) {
        val aoCRunner = AoCRunner()
        for (day in aoCRunner.allDays()) {
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
    val output = result.fold({ "\u001B[41m$it\u001B[0m" }, { "\u001B[32m$it\u001B[0m" })
    println("$ident RESULT: $output")
    println("$ident END")
}

