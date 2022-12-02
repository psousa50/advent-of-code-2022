import java.lang.RuntimeException

fun main(args: Array<String>) {

    val aoCRunner = AoCRunner()

    val runAll = args[0] == "ALL"

    if (runAll) {
        aoCRunner.showAllResults()
    } else {
        val day = args[0].toInt()
        val part = when (args[1].toInt()) {
            1 -> DayPart.ONE
            2 -> DayPart.TWO
            else -> throw RuntimeException("Invalid Part Number")
        }
        val testing = args.size > 2 && args[2] == "test"

        aoCRunner.showResult(day, part, testing)
    }
}
