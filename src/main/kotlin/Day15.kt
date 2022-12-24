import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.abs

class Day15(testing: Boolean = false) : DaySolutions(15, testing) {
    override fun partOne(): SolutionResult =
        input
            .map { parseSensor(it) }
            .let { Coverage(it) }
            .countBeaconNotPresentAtRow(2000000)
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

        fun countBeaconNotPresentAtRow(y: Int) =
            calcCoverage().getOrDefault(y, emptyList()).countPoints() - beaconsCountAtThisRow(y)

        private fun calcCoverage(): MutableMap<Int, List<Segment>> {
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

        fun beaconsCountAtThisRow(y: Int) = sensors.map { it.closestBeacon }.toSet().count { it.y == y }

        private fun List<Segment>.countPoints(): Int {
            return this.unionLength()

        }

        private fun MutableMap<Int, List<Segment>>.addSegment(y: Int, segment: Segment) {
            this[y] = this.getOrDefault(y, mutableListOf()) + segment
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

data class Segment(
    val p1: Int,
    val p2: Int
)

fun List<Segment>.unionLength(): Int {
    val pSet: MutableSet<Int> = mutableSetOf()
    for (s in this)
        for (p in s.p1..s.p2)
            pSet.add(p)

    return pSet.size
}

fun List<Segment>.unionLength2(): Int {
    val points = this
        .map { listOf(SegmentPoint(it.p1, true), SegmentPoint(it.p2, false)) }
        .flatten()
        .sortedWith(compareBy<SegmentPoint> {it.position}.thenBy { !it.isLeft })
    var count = 1
    var length = 0
    for (p in points.drop(1).indices) {
        val previous = points[p]
        val current = points[p + 1]
        if (count > 0) {
            length += current.end - previous.end
        }
        if (current.isLeft) count++ else count--
    }
    return length
}


