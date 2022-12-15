import java.util.PriorityQueue
import kotlin.math.abs

class Day12(testing: Boolean = false) : DaySolutions(12, testing) {
    override fun partOne(): SolutionResult =
        findShortPath(parse(input))
            .count()
            .minus(1)
            .bind()

    private fun findShortPath(heightMap: HeightMap): List<Point> {
        val a = AStar(heightMap.grid, heightMap.startPosition, heightMap.endPosition)
        return a.shortestPath().map { it.position }
    }

    private fun parse(input: SolutionInput): HeightMap {
        val startPosition = input.findCharPosition(START_CHAR)
        val endPosition = input.findCharPosition(END_CHAR)
        return HeightMap(
            input.replace(START_CHAR, 'a').replace(END_CHAR, 'z'),
            startPosition,
            endPosition
        )
    }

    private data class HeightMap(
        val grid: SolutionInput,
        val startPosition: Point,
        val endPosition: Point
    )

    private fun SolutionInput.findCharPosition(c: Char): Point {
        val y = this.indexOfFirst { line -> line.indexOf(c) >= 0 }
        val x = this[y].indexOf(c)
        return Point(x, y)
    }

    private fun SolutionInput.replace(c1: Char, c2: Char): SolutionInput =
        this.map { line -> line.replace(c1, c2) }

    companion object {
        const val START_CHAR = 'S'
        const val END_CHAR = 'E'
    }
}

private data class Point(val x: Int, val y: Int) {
    operator fun plus(point: Point) = Point(x + point.x, y + point.y)
    fun manhattanDistanceTo(point: Point): Int = abs(x - point.x) + abs(y - point.y)
    override fun toString() = "($x,$y)"
}

private data class Node(
    val parent: Node?,
    val position: Point,
    val g: Int,
    val h: Int
) {
    val f: Int = g + h

    fun path(): List<Node> = (parent?.path() ?: emptyList()) + this
    override fun toString() = "$position $g $h"
}

private class AStar(
    val grid: List<String>,
    val start: Point,
    val end: Point
) {

    var openNodes = PriorityQueue<Node> { n1, n2 -> n1.f.compareTo(n2.f) }
    var closedNodes = mutableListOf<Node>()

    fun shortestPath(): List<Node> {
        val startNode = Node(null, start, 0, end.manhattanDistanceTo(start))
        val openNodes = PriorityQueue<Node> { n1, n2 -> n1.f.compareTo(n2.f) }
        val closedNodes = mutableListOf<Node>()
        openNodes.add(startNode)

        println("===================== $start")
        println("===================== $end")
        var nodeFound: Node? = null
        while (openNodes.isNotEmpty() && nodeFound == null) {
            val node = openNodes.remove()
            if (node.position == end) {
                nodeFound = node
            } else {
                for (p in neighbours(node.position)) {
                    if (openNodes.none { it.position == p })
                        if (closedNodes.none { it.position == p }) {
                            val successor = Node(node, p, node.g + 1, p.manhattanDistanceTo(end))
                            openNodes.add(successor)
                        }
                }
            }
            closedNodes.add(node)
        }

        return nodeFound?.path() ?: emptyList()
    }

    private fun inGrid(p: Point) = p.x >= 0 && p.x < grid[0].length && p.y >= 0 && p.y < grid.size

    private fun height(p: Point) = grid[p.y][p.x].code - 'a'.code

    private fun neighbours(position: Point) =
        sequence {
            for (pd in listOf(
                Point(1, 0),
                Point(-1, 0),
                Point(0, 1),
                Point(0, -1),
            )) {
                val newPosition = position + pd
                if (inGrid(newPosition) && height(newPosition) - height(position) <= 1)
                    yield(newPosition)
            }
        }
}