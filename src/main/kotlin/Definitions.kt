enum class DayPart {
    ONE,
    TWO
}
typealias DayNumber = Int

typealias SolutionInput = List<String>

data class SolutionResult(val value: String) {
    val intValue get() = value.toInt()
    override fun toString() = value
}

fun Int.bind() = SolutionResult(this.toString())
fun String.bind() = SolutionResult(this)


