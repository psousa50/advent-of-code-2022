class Day08(testing: Boolean = false) : DaySolutions(8, testing) {
    override fun partOne(): SolutionResult {

        val grid = Grid(input)

        return grid
            .allTrees()
            .count { grid.isVisible(it) }
            .bind()

    }
}

private class Grid(val lines: List<String>) {

    val gridHeight get() = lines.size
    val gridWidth get() = lines[0].length
    fun allTrees(): Sequence<Tree> =
        sequence {
            for (r in 0 until gridHeight)
                for (c in 0 until gridWidth)
                    yield(treeAt(r, c))
        }

    fun pathsToEdge(tree: Tree): List<List<Tree>> =
        with(tree) {
            listOf(
                (0 until row).map { treeAt(it, column) },
                (row + 1 until gridHeight).map { treeAt(it, column) },
                (0 until column).map { treeAt(row, it) },
                (column + 1 until gridWidth).map { treeAt(row, it) },
            ).filter { it.isNotEmpty() }
        }

    fun treeAt(row: Int, column: Int): Tree =
        Tree(lines[row][column].digitToInt(), row, column)

    private fun atEdge(tree: Tree) =
        with(tree) {
            row == 0 || row == gridHeight - 1 || column == 0 || column == gridWidth - 1
        }

    fun isVisible(tree: Tree) =
        atEdge(tree) || pathsToEdge(tree).any { path ->
            path.all { it.height < tree.height }
        }
}

private data class Tree(
    val height: Int,
    val row: Int,
    val column: Int
) {
    override fun toString() = "[$row, $column] - $height"
}