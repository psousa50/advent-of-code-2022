import arrow.core.right
import java.lang.RuntimeException

enum class GameMove {
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
        choices[0] -> GameMove.ROCK
        choices[1] -> GameMove.PAPER
        choices[2] -> GameMove.SCISSOR
        else -> throw RuntimeException("Invalid move: ($letter)")
    }

fun playerOneMoves(letter: Char) = playerMoves(letter, "ABC")
fun playerTwoMoves(letter: Char) = playerMoves(letter, "XYZ")

fun p2ChoiceScore(move: GameMove) =
    when (move) {
        GameMove.ROCK -> 1
        GameMove.PAPER -> 2
        GameMove.SCISSOR -> 3
    }

fun outcomeScore(outcome: GameOutcomes) =
    when (outcome) {
        GameOutcomes.LOST -> 0
        GameOutcomes.DRAW -> 3
        GameOutcomes.WIN -> 6
    }


fun gameOutcome(p1Move: GameMove, p2Move: GameMove) =
    if (p1Move == p2Move)
        GameOutcomes.DRAW
    else when (Pair(p1Move, p2Move)) {
        Pair(GameMove.ROCK, GameMove.SCISSOR) -> GameOutcomes.WIN
        Pair(GameMove.PAPER, GameMove.ROCK) -> GameOutcomes.WIN
        Pair(GameMove.SCISSOR, GameMove.PAPER) -> GameOutcomes.WIN
        else -> GameOutcomes.LOST
    }

fun desiredOutcome(letter: Char) =
    when (letter) {
        'X' -> GameOutcomes.LOST
        'Y' -> GameOutcomes.DRAW
        'Z' -> GameOutcomes.WIN
        else -> throw RuntimeException("Invalid outcome: ($letter)")
    }


fun guessPlayer2Move(p1Move: GameMove, desiredOutcome: GameOutcomes): GameMove =
    listOf(GameMove.ROCK, GameMove.PAPER, GameMove.SCISSOR).first { gameOutcome(it, p1Move) == desiredOutcome }

class Day02(testing: Boolean = false) : Day(2, testing) {
    override fun part1(): SolutionReturnType =
        calcScore { p1, p2 -> Pair(playerOneMoves(p1), playerTwoMoves(p2)) }

    override fun part2(): SolutionReturnType =
        calcScore { p1, p2 ->
            val p1Move = playerOneMoves(p1)
            Pair(p1Move, guessPlayer2Move(p1Move, desiredOutcome(p2)))
        }


    private fun calcScore(movesResolver: (p1: Char, p2: Char) -> Pair<GameMove, GameMove>): SolutionReturnType {
        val score = getInputFile(DayPart.ONE).useLines { lines ->
            lines.fold(0) { score, line ->
                val p1 = line[0]
                val p2 = line[2]
                val (p1Move, p2Move) = movesResolver(p1, p2)
                score + p2ChoiceScore(p2Move) + outcomeScore(gameOutcome(p2Move, p1Move))
            }
        }
        return score.right()
    }
}
