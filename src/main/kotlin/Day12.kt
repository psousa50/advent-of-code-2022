import java.util.PriorityQueue
import kotlin.math.abs

class Day12(testing: Boolean = false) : DaySolutions(12, testing) {
    override fun partOne(): SolutionResult =
        findShortestPathToPeek(parse(input))
            .count()
            .minus(1)
            .bind()

    override fun partTwo(): SolutionResult =
        findShortestPathToBase(parse(input))
            .count()
            .minus(1)
            .bind()

    private fun findShortestPathToPeek(heightMap: HeightMap): List<Point> =
        AStar(
            heightMap.grid,
            heightMap.startPosition,
            heightMap.endPosition,
            final = { node -> node.position == heightMap.endPosition },
            heuristic = { n1, n2 -> n2.position.manhattanDistanceTo(n1.position) },
            validMove = { n1, n2 -> n1.height - n2.height <= 1 }
        )
            .let { it.shortestPath().map { node -> node.position } }


    private fun findShortestPathToBase(heightMap: HeightMap): List<Point> =
        AStar(
            heightMap.grid,
            heightMap.endPosition,
            heightMap.startPosition,
            final = { node -> node.height == 0 },
            heuristic = { _, _ -> 0 },
            validMove = { n1, n2 -> n1.height - n2.height >= -1 }
        )
            .let { it.shortestPath().map { node -> node.position } }

    private fun parse(input: SolutionInput): HeightMap {
        val startPosition = input.findCharPosition(START_CHAR)
        val endPosition = input.findCharPosition(END_CHAR)
        return HeightMap(
            input
                .replace(START_CHAR, 'a')
                .replace(END_CHAR, 'z')
                .map { l -> l.map { it } },
            startPosition,
            endPosition
        )
    }

    private data class HeightMap(
        val grid: List<List<Char>>,
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

private data class Point(val x: Int, val y: Int, val height: Int = 0) {
    operator fun plus(point: Point) = Point(x + point.x, y + point.y)
    fun manhattanDistanceTo(point: Point): Int = abs(x - point.x) + abs(y - point.y)
    override fun toString() = "($x,$y)"
}

private data class Node(
    val position: Point,
    val height: Int,
    val parent: Node? = null,
    val g: Int = 0,
    val h: Int = 0
) {
    val f = g + h

    fun path(): List<Node> = (parent?.path() ?: emptyList()) + this
    override fun toString() = "$position $height $g $h"
}

private class AStar(
    val grid: List<List<Char>>,
    val startPosition: Point,
    val endPosition: Point,
    val final: (node: Node) -> Boolean,
    val heuristic: (n1: Node, n2: Node) -> Int,
    val validMove: (n1: Node, n2: Node) -> Boolean
) {

    fun shortestPath(
    ): List<Node> {
        val openNodes = PriorityQueue<Node> { n1, n2 -> n1.f - n2.f }
        val closedNodes = mutableListOf<Node>()
        openNodes.add(nodeOf(startPosition))

        var nodeFound: Node? = null
        while (openNodes.isNotEmpty() && nodeFound == null) {
            val node = openNodes.remove()
            if (final(node)) {
                nodeFound = node
            } else {
                for (p in validMoves(node.position)) {
                    if (openNodes.none { it.position == p })
                        if (closedNodes.none { it.position == p }) {
                            val successor = nodeOf(p, node, node.g + 1, heuristic(nodeOf(p), nodeOf(endPosition)))
                            openNodes.add(successor)
                        }
                }
            }
            closedNodes.add(node)
        }

        return nodeFound?.path() ?: emptyList()
    }

    private fun nodeOf(
        position: Point,
        parent: Node? = null,
        g: Int = 0,
        h: Int = 0
    ): Node {
        return Node(position, height(position), parent, g, h)
    }

    private fun inGrid(p: Point) = p.x >= 0 && p.x < grid[0].size && p.y >= 0 && p.y < grid.size

    private fun height(p: Point) = grid[p.y][p.x].code - 'a'.code

    private fun validMoves(position: Point) =
        sequence {
            for (pd in listOf(
                Point(1, 0),
                Point(-1, 0),
                Point(0, 1),
                Point(0, -1),
            )) {
                val newPosition = position + pd
                if (inGrid(newPosition) && validMove(nodeOf(newPosition), nodeOf(position)))
                    yield(newPosition)
            }
        }
}