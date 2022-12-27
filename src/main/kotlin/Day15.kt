import kotlin.math.abs

class Day15(testing: Boolean = false) : DaySolutions(15, testing) {
    override fun partOne(): SolutionResult =
        input
            .map { parseSensor(it) }
            .let { Coverage(it) }
            .countCannotContainBeacon(2000000)
            .bind()

    override fun partTwo(): SolutionResult =
        input
            .map { parseSensor(it) }
            .let { Coverage(it) }
            .findRowThatCanHaveBeacon()
            .let { it.x * 4000000L + it.y }
            .bind()

    private fun parseSensor(line: String): Sensor {
        val parts = line.split(" ")
        val position = Point(parseNumber(parts[2]), parseNumber(parts[3]))
        val closestBeacon = Point(parseNumber(parts[8]), parseNumber(parts[9]))
        return Sensor(position, closestBeacon)
    }

    private fun parseNumber(s: String) =
        Regex("=(-?\\d+)").find(s)?.groupValues?.get(1)?.toInt() ?: 0

    private data class Point(val x: Int, val y: Int) {
        fun manhattanDistanceTo(point: Point): Int = abs(x - point.x) + abs(y - point.y)
        override fun toString() = "($x,$y)"
    }

    private data class Sensor(
        val position: Point,
        val closestBeacon: Point
    ) {
        val distanceToBeacon get() = position.manhattanDistanceTo(closestBeacon)

        override fun toString() = "$position -> $closestBeacon"
    }

    private class Coverage(
        val sensors: List<Sensor>
    ) {
        fun countCannotContainBeacon(y: Int) =
            calcCoverage().getOrDefault(y, emptyList())
                .union()
                .length()
                .minus(beaconsCountAtThisRow(y))

        fun calcCoverage(): MutableMap<Int, List<Segment>> {
            val rowCount: MutableMap<Int, List<Segment>> = mutableMapOf()
            for (sensor in sensors) {
                for (dy in 0 until sensor.distanceToBeacon) {
                    val x = sensor.position.x
                    val y1 = sensor.position.y - sensor.distanceToBeacon + dy
                    rowCount.addSegment(y1, Segment(x - dy, x + dy))
                    val y2 = sensor.position.y + sensor.distanceToBeacon - dy
                    rowCount.addSegment(y2, Segment(x - dy, x + dy))
                }
                rowCount.addSegment(
                    sensor.position.y,
                    Segment(
                        sensor.position.x - sensor.distanceToBeacon,
                        sensor.position.x + sensor.distanceToBeacon
                    )
                )
            }
            return rowCount
        }

        private fun MutableMap<Int, List<Segment>>.addSegment(y: Int, segment: Segment) {
            this[y] = this.getOrDefault(y, mutableListOf()) + segment
        }

        fun beaconsCountAtThisRow(y: Int) = sensors.map { it.closestBeacon }.toSet().count { it.y == y }

        fun findRowThatCanHaveBeacon(): Point {
            val rowCount = calcCoverage()
            for (y in rowCount.keys.filter { it in 0..4000000 }) {
                val segments = rowCount[y]!!
                for (s in segments.union().drop(1)) {
                    if (s.p1 in 0..4000000) {
                        return Point(s.p1, y)
                    }
                }
            }
            return Point(0, 0)
        }
    }
}

private data class SegmentPoint(
    val position: Int,
    val isLeft: Boolean
) {
    val isRight get() = !isLeft
    val end get() = position + if (isLeft) 0 else 1
}

private data class Segment(
    val p1: Int,
    val p2: Int
) {
    val length get() = p2 - p1 + 1
}

private fun List<Segment>.length() = this.fold(0) { l, s -> l + s.length }

private fun List<Segment>.union(): List<Segment> {
    val points = this
        .map { listOf(SegmentPoint(it.p1, true), SegmentPoint(it.p2, false)) }
        .flatten()
        .sortedWith(compareBy<SegmentPoint> { it.position }.thenBy { !it.isLeft })
    val segments: MutableList<Segment> = mutableListOf()
    var count = 1
    var start = points[0].position
    for (p in points.drop(1).indices) {
        val current = points[p + 1]
        if (current.isLeft) count++ else count--
        if (count == 0) {
            segments.add(Segment(start, current.position))
            start = current.end
        }
    }
    return segments
}

