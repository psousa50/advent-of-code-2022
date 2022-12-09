import kotlin.math.abs
import kotlin.math.sign

class Day09(testing: Boolean = false) : DaySolutions(9, testing) {
    override fun partOne(): SolutionResult =
        input
            .map { parse(it) }
            .fold(Rope()) { rope, motion ->
                rope.move(motion)
            }
            .visited
            .count()
            .bind()

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
        private val head: Point = Point(0, 0),
        private val tail: Point = Point(0, 0),
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
            val newHead = head + move
            val newTail = if (tail isToFarFrom newHead) tail + (head - tail).normalized() else tail
            return Rope(newHead, newTail, visited + newTail)
        }

        private infix fun Point.isToFarFrom(other: Point) =
            abs(this.x - other.x) >= 2 || abs(this.y - other.y) >= 2
    }
}

