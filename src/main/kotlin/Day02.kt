import arrow.core.right
import java.lang.RuntimeException

enum class GameMoves {
    ROCK,
    PAPER,
    SCISSOR
}

enum class GameOutcomes {
    LOST,
    DRAW,
    WIN
}

fun playerMoves(letter: Char, choices: String) =
    when (letter) {
        choices[0] -> GameMoves.ROCK
        choices[1] -> GameMoves.PAPER
        choices[2] -> GameMoves.SCISSOR
        else -> throw RuntimeException("Invalid move: ($letter)")
    }

fun playerOneMoves(letter: Char) = playerMoves(letter, "ABC")
fun playerTwoMoves(letter: Char) = playerMoves(letter, "XYZ")

fun p2ChoiceScore(move: GameMoves) =
    when (move) {
        GameMoves.ROCK -> 1
        GameMoves.PAPER -> 2
        GameMoves.SCISSOR -> 3
    }

fun outcomeScore(outcome: GameOutcomes) =
    when (outcome) {
        GameOutcomes.LOST -> 0
        GameOutcomes.DRAW -> 3
        GameOutcomes.WIN -> 6
    }


fun gameOutcome(p1Move: GameMoves, p2Move: GameMoves) =
    if (p1Move == p2Move)
        GameOutcomes.DRAW
    else when (Pair(p1Move, p2Move)) {
        Pair(GameMoves.ROCK, GameMoves.SCISSOR) -> GameOutcomes.WIN
        Pair(GameMoves.PAPER, GameMoves.ROCK) -> GameOutcomes.WIN
        Pair(GameMoves.SCISSOR, GameMoves.PAPER) -> GameOutcomes.WIN
        else -> GameOutcomes.LOST
    }

fun guessPlayer2Move(p1Move: GameMoves, desiredOutcome: GameOutcomes): GameMoves {
    return GameMoves.SCISSOR
}
class Day02(testing: Boolean = false) : Day(2, testing) {
    override fun part1(): SolutionReturnType {
        val score = getInputFile(DayPart.ONE).useLines { lines ->
            lines.fold(0) { score, line ->
                val p1 = line[0]
                val p2 = line[2]
                val p1Move = playerOneMoves(p1)
                val p2Move = playerTwoMoves(p2)
                score + p2ChoiceScore(p2Move)+ outcomeScore(gameOutcome(p2Move, p1Move))
            }
        }
        return score.right()
    }
}
