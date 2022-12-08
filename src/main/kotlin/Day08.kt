class Day08(testing: Boolean = false) : DaySolutions(8, testing) {
    override fun partOne(): SolutionResult =
        with(Grid(input)) {
            allTrees()
                .count { isVisible(it) }
                .bind()
        }

    override fun partTwo(): SolutionResult =
        with(Grid(input)) {
            allTrees()
                .map { scenicScore(it) }
                .max()
                .bind()
        }

    private class Grid(val lines: List<String>) {
        val gridHeight get() = lines.size
        val gridWidth get() = lines[0].length

        fun allTrees() =
            sequence {
                for (r in 0 until gridHeight)
                    for (c in 0 until gridWidth)
                        yield(treeAt(r, c))
            }

        fun pathsToEdge(tree: Tree): List<List<Tree>> =
            with(tree) {
                listOf(
                    (0 until row).reversed().map { treeAt(it, column) },
                    (row + 1 until gridHeight).map { treeAt(it, column) },
                    (0 until column).reversed().map { treeAt(row, it) },
                    (column + 1 until gridWidth).map { treeAt(row, it) },
                )
            }

        fun treeAt(row: Int, column: Int) =
            Tree(lines[row][column].digitToInt(), row, column)

        fun isVisible(tree: Tree) =
            pathsToEdge(tree).any { path ->
                path.all { it.height < tree.height }
            }

        fun scenicScore(tree: Tree) =
            pathsToEdge(tree)
                .map { countVisible(tree, it) }
                .reduce { product, v -> product * v }

        private fun countVisible(tree: Tree, path: List<Tree>): Int =
            with(path.indexOfFirst { it.height >= tree.height }) {
                if (this < 0) path.size else this + 1
            }
    }
}

private data class Tree(
    val height: Int,
    val row: Int,
    val column: Int
) {
    override fun toString() = "[$row, $column] - $height"
}