import java.lang.Math.round
import kotlin.math.roundToInt

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
            else -> throw NotImplementedError("Invalid Part Number")
        }
        val testing = args.size > 2 && args[2] == "test"

        val aoCRunner = AoCRunner(testing)

        showResult(aoCRunner, day, part)
    }
}

fun showResult(aoCRunner: AoCRunner, dayNumber: DayNumber, dayPart: DayPart) {
    val start = System.currentTimeMillis()
    val result = aoCRunner.runPart(dayNumber, dayPart)
    val end = System.currentTimeMillis()
    println("Day $dayNumber, part $dayPart")
    println("\u001B[32m$result\u001B[0m")
    println("Duration: ${((end - start.toDouble()) / 10.0).roundToInt() / 100.0}s")
}

