import java.lang.Integer.max
import java.util.*

class Day16(testing: Boolean = false) : DaySolutions(16, testing) {
    override fun partOne(): SolutionResult =
        input
            .map { parseValve(it) }
            .associateBy { it.name }
            .let { Cave(it) }
            .let { it.findPath() }
            .map {
                with(it.state) {
                    println("${timeLeft}:\t${valve.name}:${valve.flowRate}\t${if (open) "OPEN" else "CLOSED"}\t$opened\t->\t${it.state.totalPressure}")
                }
                it
            }
            .fold(0) { p, n -> p + n.state.totalPressure }
            .bind()


    private fun parseValve(line: String): Valve {
        val p = line.split((" "))
        return Valve(
            p[1],
            p[4].split("=")[1].split(";")[0].toInt(),
            p.drop(9).map { it.replace(",", "") }
        )
    }

    private class Cave(val valves: Map<String, Valve>) {

        val maxEstimatedPressure = valves.values.sumOf { it.flowRate } * duration

        fun valve(name: String) = valves[name]!!

        private fun final(node: Node) = node.state.timeLeft == 0

        @Suppress("UNUSED_PARAMETER")
        private fun cost(node: Node, nodeState: NodeState): Int {
            return node.g + maxEstimatedPressure - nodeState.valve.flowRate
        }

        @Suppress("UNUSED_PARAMETER")
        private fun heuristic(node: Node, nodeState: NodeState) : Int {
            return maxEstimatedPressure - if (nodeState.opened) nodeState.valve.flowRate * nodeState.timeLeft else 0
        }

        private fun validMoves(nodeState: NodeState): List<NodeState> {
            val moves = nodeState.valve.leadsTo
                .map {
                    NodeState(
                        valve(it),
                        timeLeft = nodeState.timeLeft - 1,
                        openValves = nodeState.openValves
                    )
                }
            return if (nodeState.valve.name !in nodeState.openValves && nodeState.valve.name != "AA")
                moves + NodeState(
                    valve(nodeState.valve.name),
                    timeLeft = nodeState.timeLeft - 1,
                    opened = true,
                    openValves = nodeState.openValves + nodeState.valve.name
                )
            else moves
        }

        fun findPath() = AStar(
            NodeState(valve("AA")),
            { final(it) },
            { node, nodeState -> cost(node, nodeState) },
            { node, nodeState -> heuristic(node, nodeState) },
            { node -> validMoves(node) }
        ).find()
    }

    data class Valve(
        val name: String,
        val flowRate: Int,
        val leadsTo: List<String>,
    ) {
        override fun toString() = "$name $flowRate $leadsTo"
    }

    data class NodeState(
        val valve: Valve,
        val timeLeft: Int = duration,
        val opened: Boolean = false,
        val openValves: Set<String> = emptySet()
    ) {
        val open get() = valve.name in openValves
        val totalPressure get() = if (opened) valve.flowRate * timeLeft else 0
        fun isSame(other: NodeState) =
            valve.name == other.valve.name && timeLeft == other.timeLeft && opened == other.opened

        override fun toString() = "$valve open:$open tl:$timeLeft opened:$opened"
    }

    data class Node(
        val state: NodeState,
        val parent: Node? = null,
        val g: Int = 0,
        val h: Int = 0
    ) {
        val f = g + h

        fun path(): List<Node> = (parent?.path() ?: emptyList()) + this
        override fun toString() = "state:$state g:$g h:$h f: $f"
    }

    class AStar(
        private val initialNode: NodeState,
        private val final: (node: Node) -> Boolean,
        private val cost: (n: Node, nodeState: NodeState) -> Int,
        private val heuristic: (node: Node, nodeState: NodeState) -> Int,
        private val validMoves: (nodeState: NodeState) -> List<NodeState>
    ) {
        fun find(): List<Node> {
            val openNodes = PriorityQueue<Node> { n1, n2 -> n1.f - n2.f }
            val closedNodes = mutableListOf<Node>()
            openNodes.add(Node(initialNode))

            var nodeFound: Node? = null
            while (openNodes.isNotEmpty() && nodeFound == null) {
                val currentNode = openNodes.remove()
//                println("$currentNode ${currentNode.path().map { it.state.valve.name }}")
                if (final(currentNode)) {
                    nodeFound = currentNode
                } else {
                    val moves = validMoves(currentNode.state)
                    for (nodeState in moves) {
                        if (openNodes.none { it.state == nodeState })
                            if (closedNodes.none { it.state == nodeState }) {
                                val successor = Node(
                                    nodeState,
                                    currentNode,
                                    cost(currentNode, nodeState),
                                    heuristic(currentNode, nodeState)
                                )
                                openNodes.add(successor)
                            }
                    }
                }
                closedNodes.add(currentNode)
            }
            if (nodeFound == null) println("NOT FOUND!")
            return nodeFound?.path() ?: emptyList()
        }
    }

    companion object {
        const val duration = 30
    }
}
