import java.lang.Integer.min

class Day13(testing: Boolean = false) : DaySolutions(13, testing) {
    override fun partOne(): SolutionResult =
        input
            .splitByBlankLine { lines -> lines.map { parsePairOfPackets(it) } }
            .mapIndexed { i, pair -> if (pair.first <= pair.second) i + 1 else 0 }
            .sum()
            .bind()

    private fun parsePairOfPackets(lines: List<String>) =
        Pair(
            PacketParser().parse(lines[0]),
            PacketParser().parse(lines[1]),
        )

    private sealed interface Value {
        val size: Int
        fun toPacket(): Packet
        operator fun get(i: Int): Value
    }

    private data class IntValue(val value: Int) : Value {
        override val size get() = 1
        override fun get(i: Int) = this
        override fun toPacket() = Packet(listOf(this))
        operator fun compareTo(other: IntValue) = this.value.compareTo(other.value)

        override fun toString() = value.toString()
    }

    private data class Packet(
        val packets: List<Value>
    ) : Value {

        override val size get() = packets.size
        override fun get(i: Int) = packets[i]
        override fun toPacket() = this

        operator fun compareTo(other: Packet): Int {
            val firstDifferent = packets.subList(0, min(size, other.size))
                .mapIndexed { i, v -> Pair(v, other[i]) }
                .firstOrNull { compare(it) != 0 }

            return when (firstDifferent) {
                null -> size.compareTo(other.size)
                else -> compare(firstDifferent)
            }
        }

        private fun compare(pair: Pair<Value, Value>): Int {
            val first = pair.first
            val second = pair.second
            return when {
                first is IntValue && second is IntValue -> first.compareTo(second)
                first is Packet && second is Packet -> first.compareTo(second)
                else -> first.toPacket().compareTo(second.toPacket())
            }
        }

        override fun toString() = "[${packets.joinToString(",")}]"
    }

    private class PacketString(
        val line: String,
        var cursor: Int = 0
    ) {
        val end get() = cursor >= line.length
        val peek get() = line[cursor]
        fun moveNext() = also { cursor++ }
        fun takeWhile(predicate: (c: Char) -> Boolean) =
            line
                .substring(cursor)
                .takeWhile(predicate)
                .also { cursor += it.length }

        fun skip(c: Char) = also { if (!end && peek == c) moveNext() }

        fun number() = takeWhile { it.isDigit() }.toInt()
    }

    private class PacketParser() {
        fun parse(line: String) = parse(PacketString(line))
        private fun parse(packetString: PacketString): Packet {
            packetString.moveNext()
            val packets = mutableListOf<Value>()
            while (packetString.peek != ']') {
                val value =
                    if (packetString.peek == '[')
                        parse(packetString)
                    else
                        IntValue(packetString.number())
                packets.add(value)
                packetString.skip(',')
            }
            packetString.skip(']')
            packetString.skip(',')
            return Packet(packets)
        }


    }
}
