import kotlin.math.abs
import kotlin.math.sign

class Day09(testing: Boolean = false) : DaySolutions(9, testing) {
    override fun partOne(): SolutionResult =
        moveRope(List(2) {origin})

    override fun partTwo(): SolutionResult =
        moveRope(List(10) {origin})

    private fun moveRope(knots: List<Point>): SolutionResult =
        input
            .map { parse(it) }
            .fold(Rope(knots)) { rope, motion -> rope.move(motion) }
            .visited
            .count()
            .bind()

    private val origin get() = Point(0,0)

    private fun parse(line: String) =
        with(line.split(" ")) {
            Motion(this[0], this[1].toInt())
        }

    data class Motion(val direction: String, val amount: Int)

    data class Point(val x: Int, val y: Int) {
        operator fun plus(point: Point) = Point(x + point.x, y + point.y)
        operator fun minus(point: Point) = Point(x - point.x, y - point.y)
        private fun normalized(v: Int) = sign(v.toDouble()).toInt()
        fun normalized() = Point(normalized(x), normalized(y))
    }

    class Rope(
        private val knots: List<Point>,
        val visited: Set<Point> = setOf(),
    ) {
        fun move(motion: Motion): Rope =
            (0 until motion.amount).fold(this) { rope, _ -> rope.move(motion.direction) }

        private fun move(direction: String): Rope {
            val move = when (direction) {
                "R" -> Point(1, 0)
                "U" -> Point(0, 1)
                "L" -> Point(-1, 0)
                "D" -> Point(0, -1)
                else -> Point(0, 0)
            }
            val newHead = knots.first() + move

            val newKnots = knots.drop(1).mapIndexed { i, knot ->
                val knotAhead = knots[i]
                if (knot isToFarFrom knotAhead) knot + (knotAhead - knot).normalized() else knot
            }
            return Rope(listOf(newHead) + newKnots,visited + newKnots.last())
        }

        private infix fun Point.isToFarFrom(other: Point) =
            abs(this.x - other.x) >= 2 || abs(this.y - other.y) >= 2
    }
}

