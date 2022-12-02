import java.lang.RuntimeException

enum class GameChoices {
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
        choices[0] -> GameChoices.ROCK
        choices[1] -> GameChoices.PAPER
        choices[2] -> GameChoices.SCISSOR
        else -> throw RuntimeException("Invalid move: ($letter)")
    }

fun playerOneMoves(letter: Char) = playerMoves(letter, "ABC")
fun playerTwoMoves(letter: Char) = playerMoves(letter, "XYZ")

fun p2ChoiceScore(move: GameChoices) =
    when (move) {
        GameChoices.ROCK -> 1
        GameChoices.PAPER -> 2
        GameChoices.SCISSOR -> 3
    }

fun outcomeScore(outcome: GameOutcomes) =
    when (outcome) {
        GameOutcomes.LOST -> 0
        GameOutcomes.DRAW -> 3
        GameOutcomes.WIN -> 6
    }


fun gameOutcome(p1Move: GameChoices, p2Move: GameChoices) =
    if (p1Move == p2Move)
        GameOutcomes.DRAW
    else when (Pair(p1Move, p2Move)) {
        Pair(GameChoices.ROCK, GameChoices.SCISSOR) -> GameOutcomes.WIN
        Pair(GameChoices.PAPER, GameChoices.ROCK) -> GameOutcomes.WIN
        Pair(GameChoices.SCISSOR, GameChoices.PAPER) -> GameOutcomes.WIN
        else -> GameOutcomes.LOST
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
        return score.toString()
    }
}

//   R P S
// R d l w
// P w d l
// S l w d