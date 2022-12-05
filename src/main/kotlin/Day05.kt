class Day05(testing: Boolean = false) : DaySolutions(5, testing) {
    override fun partOne(): SolutionResult =
        input
            .splitOnBlankLines { (stacksInput, movesInput) ->
                applyMoves(parseStacks(stacksInput), parseMoves(movesInput))
            }
            .map { stack ->stack.first() }.joinToString("")

    private fun <A> List<String>.splitOnBlankLines(process: (lines: List<List<String>>) -> A): A {
        val lineGroups = this.fold(listOf(listOf<String>())) { lists, line ->
            when {
                line.isEmpty() -> lists + listOf(listOf())
                else -> lists.dropLast(1) + listOf(lists.last() + line)
            }
        }
        return process(lineGroups)
    }

    private fun parseStacks(input: List<String>): List<List<Char>> {
        val crateLines = input
            .dropLast(1)
            .reversed()
            .map { it.chunked(4).map { s -> s[1] } }

        val emptyStacks = List(crateLines.first().size) { listOf<Char>() }
        return crateLines.fold(emptyStacks) { lists, line ->
            lists.mapIndexed { i, stack -> stack + line[i] }
        }.map { it.filter { c -> isCrate(c) } }.map { it.reversed() }
    }

    private fun isCrate(c: Char) = c.isUpperCase()

    private fun parseMoves(input: List<String>): List<Move> {
        return input.map { it.split(' ') }
            .map { it.filter { s -> s[0].isDigit() } }
            .map { Move(it[0].toInt(), it[1].toInt(), it[2].toInt()) }
    }

    private fun applyMoves(stacks: List<List<Char>>, moves: List<Move>): List<List<Char>> {
        return moves.fold(stacks) { newStacks, move ->
            newStacks.mapIndexed() { i, stack ->
                when (i + 1) {
                    move.from -> stack.drop(move.count)
                    move.to -> newStacks[move.from - 1].take(move.count).reversed() + stack
                    else -> stack
                }
            }
        }
    }
}


data class Move(
    val count: Int,
    val from: Int,
    val to: Int
)

