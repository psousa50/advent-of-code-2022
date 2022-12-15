enum class DayPart {
    ONE,
    TWO
}
typealias DayNumber = Int

typealias SolutionInput = List<String>

data class SolutionResult(val value: String) {
    val intValue get() = value.toInt()
    val longValue get() = value.toLong()
    override fun toString() = value
}

fun Int.bind() = SolutionResult(this.toString())
fun Long.bind() = SolutionResult(this.toString())
fun String.bind() = SolutionResult(this)


