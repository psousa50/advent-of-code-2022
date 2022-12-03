import arrow.core.Either

enum class DayPart {
    ONE,
    TWO
}
typealias DayNumber = Int

typealias SolutionInput = List<String>
typealias SolutionResult = Either<String, Int>

