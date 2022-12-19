import java.lang.Integer.max
import java.lang.Integer.min

class Day14(testing: Boolean = false) : DaySolutions(14, testing) {
    override fun partOne(): SolutionResult =
        input
            .map { parsePath(it) }
            .let { it.buildCave() }
            .dropSandUnit(STARTING_POINT)
            .count()
            .minus(1)
            .bind()

    override fun partTwo(): SolutionResult =
        input
            .map { parsePath(it) }
            .let { it.buildCave(2) }
            .dropSandUnit(STARTING_POINT)
            .count()
            .bind()

    private fun parsePath(line: String) =
        line
            .split(" -> ")
            .map { parsePoint(it) }

    private fun parsePoint(line: String) =
        line.split(",")
            .let { Point(it[0].toInt(), it[1].toInt()) }

    private fun List<List<Point>>.buildCave(virtualFloorDistance: Int? = null): Cave {
        val maxY = this.map { it.map { p -> p.y } }.flatten().max()
        val initialCave = Cave(maxY + (virtualFloorDistance ?: 2), virtualFloorDistance != null)
        return this.fold(initialCave) { cave, path -> cave.also { it.addRocks(path) } }
    }

    private data class Point(val x: Int, val y: Int) {
        operator fun plus(point: Point) = Point(x + point.x, y + point.y)
        override fun toString() = "($x,$y)"
    }

    private data class Obstacle(
        val type: Type,
        val point: Point
    ) {
        enum class Type(val printableChar: Char) {
            ROCK('#'),
            SAND('o'),
            EMPTY('.'),
        }

        override fun toString() = "$type $point"
    }

    private class Cave(
        val floorLevel: Int,
        val virtualFloor: Boolean,
    ) {
        val obstacles: MutableMap<Point, Obstacle.Type> = mutableMapOf()

        fun obstacleAt(p: Point) =
            if (virtualFloor && p.y >= floorLevel)
                Obstacle.Type.ROCK
            else
                obstacles[p] ?: Obstacle.Type.EMPTY

        fun addObstacle(o: Obstacle) {
            obstacles[o.point] = o.type
        }

        fun addRocks(path: List<Point>) =
            path.drop(1)
                .forEachIndexed { index, point -> draw(path[index], point) }

        private fun draw(p1: Point, p2: Point) {
            for (x in min(p1.x, p2.x)..max(p1.x, p2.x))
                for (y in min(p1.y, p2.y)..max(p1.y, p2.y))
                    addObstacle(Obstacle(Obstacle.Type.ROCK, Point(x, y)))
        }

        fun dropSandUnit(unit: Point) =
            generateSequence(unit) {
                val restAt = drop(unit).toList().last()
                if (restAt.y < floorLevel) {
                    addObstacle(Obstacle(Obstacle.Type.SAND, restAt))
                }
                restAt.takeIf { restAt.y < floorLevel && restAt != STARTING_POINT }
            }

        private fun drop(unit: Point) =
            sequenceOf(unit) + generateSequence(unit) { u ->
                availableMoves
                    .map { move -> u + move }
                    .firstOrNull { obstacleAt(it) == Obstacle.Type.EMPTY && it.y <= floorLevel }
            }

        override fun toString(): String {
            val allX = obstacles.keys.map { it.x }
            val allY = obstacles.keys.map { it.y }
            return (allY.min()..allY.max())
                .joinToString(System.lineSeparator()) { y ->
                    (allX.min()..allX.max()).joinToString("") { x ->
                        obstacleAt(Point(x, y)).printableChar.toString()
                    }
                }
        }

        private companion object {
            val availableMoves = listOf(Point(0, 1), Point(-1, 1), Point(+1, 1))
        }
    }

    private companion object {
        val STARTING_POINT = Point(500, 0)
    }
}

