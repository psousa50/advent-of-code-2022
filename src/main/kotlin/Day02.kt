import arrow.core.right
import java.lang.RuntimeException

class Solution02Part1() : Solution(2, DayPart.ONE) {
    override fun run(input: SolutionInput) = calcScore(input) { p1, p2 -> Pair(playerOneMove(p1), playerTwoMove(p2)) }
}

class Solution02Part2() : Solution(2, DayPart.TWO) {
    override fun run(input: SolutionInput) = calcScore(input) { p1, p2 ->
        val p1Move = playerOneMove(p1)
        Pair(p1Move, determinePlayer2Move(p1Move, desiredOutcome(p2)))
    }
}

class Day02() : Day(
    Solution02Part1(),
    Solution02Part2(),
)

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

private fun calcScore(
    input: SolutionInput,
    movesResolver: (p1: Char, p2: Char) -> Pair<GameMove, GameMove>
): SolutionResult {
    val score = input.fold(0) { score, line ->
        val p1 = line[0]
        val p2 = line[2]
        val (p1Move, p2Move) = movesResolver(p1, p2)
        score + player2MoveScore(p2Move) + outcomeScore(gameOutcome(p2Move, p1Move))
    }
    return score.right()
}

private fun playerMoves(letter: Char, choices: String) =
    when (letter) {
        choices[0] -> GameMove.ROCK
        choices[1] -> GameMove.PAPER
        choices[2] -> GameMove.SCISSOR
        else -> throw RuntimeException("Invalid move: ($letter)")
    }

private fun playerOneMove(letter: Char) = playerMoves(letter, "ABC")
private fun playerTwoMove(letter: Char) = playerMoves(letter, "XYZ")

private fun player2MoveScore(move: GameMove) =
    when (move) {
        GameMove.ROCK -> 1
        GameMove.PAPER -> 2
        GameMove.SCISSOR -> 3
    }

private fun outcomeScore(outcome: GameOutcomes) =
    when (outcome) {
        GameOutcomes.LOST -> 0
        GameOutcomes.DRAW -> 3
        GameOutcomes.WIN -> 6
    }

private fun gameOutcome(p1Move: GameMove, p2Move: GameMove) =
    if (p1Move == p2Move)
        GameOutcomes.DRAW
    else
        if (listOf(
                Pair(GameMove.ROCK, GameMove.SCISSOR),
                Pair(GameMove.PAPER, GameMove.ROCK),
                Pair(GameMove.SCISSOR, GameMove.PAPER)
            ).contains(Pair(p1Move, p2Move))
        ) GameOutcomes.WIN
        else GameOutcomes.LOST

private fun desiredOutcome(letter: Char) =
    when (letter) {
        'X' -> GameOutcomes.LOST
        'Y' -> GameOutcomes.DRAW
        'Z' -> GameOutcomes.WIN
        else -> throw RuntimeException("Invalid outcome: ($letter)")
    }

private fun determinePlayer2Move(p1Move: GameMove, desiredOutcome: GameOutcomes): GameMove =
    listOf(GameMove.ROCK, GameMove.PAPER, GameMove.SCISSOR).first { gameOutcome(it, p1Move) == desiredOutcome }

