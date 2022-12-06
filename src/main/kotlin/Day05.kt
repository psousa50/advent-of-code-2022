class Day05(testing: Boolean = false) : DaySolutions(5, testing) {
    override fun partOne() = moveCrates(input, pickMultipleCratesAtOnce = false)

    override fun partTwo() = moveCrates(input, pickMultipleCratesAtOnce = true)

    private fun moveCrates(input: SolutionInput, pickMultipleCratesAtOnce: Boolean) =
        input.splitByBlankLine { (stacksInput, movesInput) ->
            applyMoves(
                parseStacks(stacksInput),
                parseMoves(movesInput),
                pickMultipleCratesAtOnce,
            )
        }.map { stack -> stack.first() }.joinToString("")

    private fun parseStacks(input: List<String>): List<Stack> {
        val cratesByLines = input
            .dropLast(1)
            .reversed()
            .map { it.chunked(4).map { s -> s[1] } }

        return transpose(cratesByLines)
            .map { it.filter { c -> isCrate(c) } }
            .map { it.reversed() }
    }

    private fun transpose(cratesByLines: List<Stack>): List<Stack> {
        val emptyStacks = List(cratesByLines.first().size) { emptyStack() }
        return cratesByLines.fold(emptyStacks) { lists, line ->
            lists.mapIndexed { i, stack -> stack + line[i] }
        }
    }

    private fun isCrate(c: Char) = c.isUpperCase()

    private fun parseMoves(input: List<String>): List<Move> {
        return input.map { it.split(' ') }
            .map { it.filter { s -> s[0].isDigit() } }
            .map { Move(it[0].toInt(), it[1].toInt(), it[2].toInt()) }
    }

    private fun applyMoves(
        stacks: List<Stack>,
        moves: List<Move>,
        pickMultipleCratesAtOnce: Boolean
    ): List<Stack> =
        moves.fold(stacks) { newStacks, move ->
            newStacks.mapIndexed() { i, stack ->
                when (i + 1) {
                    move.from -> stack.drop(move.count)
                    move.to -> newStacks[move.from - 1]
                        .take(move.count)
                        .direction(reversed = !pickMultipleCratesAtOnce) + stack

                    else -> stack
                }
            }
        }
}


private fun <E> List<E>.direction(reversed: Boolean): List<E> =
    if (reversed) this.reversed() else this

private fun emptyStack() = listOf<Char>()

data class Move(
    val count: Int,
    val from: Int,
    val to: Int
)

typealias Stack = List<Char>