class Day13(testing: Boolean = false) : DaySolutions(13, testing) {
    override fun partOne(): SolutionResult =
        input
            .splitByBlankLine { lines -> lines.map { parse(it) } }
            .map {
                println("${it.first <= it.second} ${it.first}")
                println("${it.first <= it.second} ${it.second}")
                println("")
//                println("RR ===================== ${it.first <= it.second}")
                it
            }
            .mapIndexed { i, pair -> if (pair.first <= pair.second) i + 1 else 0 }
            .sum()
            .bind()

    private fun parse(lines: List<String>) =
        Pair(
            parsePacket(Parser(lines[0]).moveNext()),
            parsePacket(Parser(lines[1]).moveNext()),
        )

    private fun parsePacket(parser: Parser): Packet {
        val packets = mutableListOf<Value>()
        while (parser.peek != ']') {
            val value =
                if (parser.peek == '[')
                    parsePacket(parser.moveNext())
                else
                    IntValue(parser.number())
            packets.add(value)
            parser.skip(',')
        }
        parser.skip(']')
        parser.skip(',')
        return Packet(packets)
    }

    sealed interface Value {
        val size: Int
        fun toPacket(): Value
        operator fun get(i: Int): Value
        operator fun compareTo(other: Value): Int
    }

    private data class IntValue(val value: Int) : Value {
        override val size get() = 1
        override fun get(i: Int) = this
        override fun toPacket() = Packet(listOf(this))
        override fun compareTo(other: Value): Int =
            when (other) {
                is IntValue -> this.value.compareTo(other.value)
                is Packet -> 0
            }

        override fun toString() = value.toString()
    }

    private data class Packet(
        val packets: List<Value>
    ) : Value {

        override val size get() = packets.size
        override fun get(i: Int) = packets[i]
        override fun toPacket() = this

        override operator fun compareTo(other: Value): Int {
            var bigger = false
            var smaller = false

            @Suppress("LoopWithTooManyJumpStatements")
            for (i in 0 until size) {
                if (i >= other.size) {
                    bigger = true
                    break
                }
                val thisValue = if (this[i]::class != other[i]::class) this[i].toPacket() else this[i]
                val otherValue = if (this[i]::class != other[i]::class) other[i].toPacket() else other[i]
                if (thisValue > otherValue) {
                    bigger = true
                    break
                }
                if (thisValue < otherValue) {
                    smaller = true
                    break
                }
            }
            return when {
                bigger -> 1
                smaller -> -1
                else -> if (size < other.size) -1 else 0
            }
        }

        override fun toString() =
            "[${packets.joinToString(",")}]"
    }

    private class Parser(val line: String) {
        var cursor = 0

        val end get() = cursor >= line.length
        val peek get() = line[cursor]
        fun moveNext() = also { cursor++ }
        fun takeWhile(p: (c: Char) -> Boolean): String {
            val s = line.substring(cursor).takeWhile(p)
            cursor += s.length
            return s
        }

        fun skip(c: Char) = also { if (!end && peek == c) moveNext() }

        fun number() = takeWhile { it.isDigit() }.toInt()
    }
}

