import java.lang.Integer.max
import java.lang.Integer.min

class Day14(testing: Boolean = false) : DaySolutions(14, testing) {
    override fun partOne(): SolutionResult =
        input
            .map { parsePath(it) }
            .let { it.toCave() }
            .dropSand()
            .count()
            .minus(1)
            .bind()

    private fun parsePath(line: String) =
        line
            .split(" -> ")
            .map { parsePoint(it) }

    private fun parsePoint(line: String) =
        line.split(",")
            .let { Point(it[0].toInt(), it[1].toInt()) }

    private data class Point(val x: Int, val y: Int) {
        operator fun plus(point: Point) = Point(x + point.x, y + point.y)
        override fun toString() = "($x,$y)"
    }

    private class Cave(
        val minX: Int,
        val maxX: Int,
        val minY: Int,
        val maxY: Int,
    ) {
        private val grid: MutableList<MutableList<Char>> =
            MutableList(maxY - minY + 1) { MutableList(maxX - minX + 1) { EMPTY } }

        fun draw(path: List<Point>): Cave {
            path.drop(1)
                .forEachIndexed { index, point -> draw(path[index], point) }

            return this
        }

        private fun valid(p: Point) = p.x in minX..maxX && p.y in minY..maxY
        operator fun get(p: Point) = if (valid(p)) grid[p.y - minY][p.x - minX] else EMPTY
        operator fun set(p: Point, c: Char) {
            if (valid(p)) grid[p.y - minY][p.x - minX] = c
        }

        private fun draw(p1: Point, p2: Point) {
            for (x in min(p1.x, p2.x)..max(p1.x, p2.x))
                for (y in min(p1.y, p2.y)..max(p1.y, p2.y))
                    this[Point(x, y)] = ROCK
        }

        fun dropSand() = dropSandUnit(Point(500, 0))

        private fun dropSandUnit(unit: Point) =
            generateSequence(unit) {
                drop(unit)?.also { this[it] = REST }
            }

        private fun drop(unit: Point): Point? {
            val moves = listOf(Point(0, 1), Point(-1, 1), Point(+1, 1))
            return generateSequence(unit) { u ->
                moves
                    .map { move -> u + move }
                    .firstOrNull { this[it] == EMPTY && it.y <= maxY }
            }
                .lastOrNull()
                ?.let { if (it.y >= maxY) null else it }
        }

        override fun toString() = grid.joinToString(System.lineSeparator()) { it.joinToString("") }
    }

    private fun List<List<Point>>.toCave(): Cave {
        val allX = this.map { it.map { p -> p.x } }.flatten()
        val allY = this.map { it.map { p -> p.y } }.flatten()
        return this
            .fold(
                Cave(
                    allX.min(),
                    allX.max(),
                    0,
                    allY.max()
                )
            ) { cave, path -> cave.draw(path) }
    }

    companion object {
        const val EMPTY = '.'
        const val ROCK = '#'
        const val REST = 'o'
    }
}

