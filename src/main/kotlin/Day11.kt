class Day11(testing: Boolean = false) : DaySolutions(11, testing) {
    override fun partOne(): SolutionResult =
        (0 until 20)
            .fold(parse(input)) { monkeys, _ -> monkeys.round() }
            .monkeys.map { it.inspectedItems }
            .sortedDescending()
            .take(2)
            .reduce { product, v -> product * v }
            .bind()

    private fun parse(input: SolutionInput): Monkeys =
        Monkeys(input.chunked(7) { parseMonkey(it) }.toMutableList())

    private fun parseMonkey(monkeyLines: List<String>): Monkey {
        return Monkey(
            parseIntParts(monkeyLines[1], "Starting items:", ", "),
            parseParts(monkeyLines[2], "Operation: new = old").toOperation(),
            parseInt(monkeyLines[3], "Test: divisible by"),
            parseInt(monkeyLines[4], "If true: throw to monkey"),
            parseInt(monkeyLines[5], "If false: throw to monkey"),
        )
    }

    private fun parseInt(line: String, prefix: String, separator: String = " "): Int =
        parseIntParts(line, prefix, separator).first()

    private fun parseIntParts(line: String, prefix: String, separator: String = " "): List<Int> =
        parseParts(line, prefix, separator).map { it.toInt() }

    private fun parseParts(line: String, prefix: String, separator: String = " "): List<String> =
        line.trim().drop(prefix.length).trim().split(separator)

    class Monkeys(
        var monkeys: MutableList<Monkey>
    ) {
        fun round(): Monkeys {
            monkeys.forEach {
            }
            for (m in monkeys.indices) {
                val (monkey, throwTo) = monkeys[m].turn()
                monkeys[m] = monkey
                throwTo.forEach {
                    monkeys[it.monkeyIndex] = monkeys[it.monkeyIndex].acceptItem(it.itemWorry)
                }
            }
            return this
        }
    }

    data class Monkey(
        val itemWorries: List<Int>,
        val operation: Operation,
        val divisibleByTest: Int,
        val monkeyToThrowOnTrue: Int,
        val monkeyToThrowOnFalse: Int,
        val inspectedItems: Int = 0
    ) {
        fun turn(): Pair<Monkey, List<ThrowTo>> =
            Pair(this.copy(itemWorries = emptyList(), inspectedItems = inspectedItems + itemWorries.size),
                itemWorries.map {
                    val worryLevel = operation(it) / 3
                    ThrowTo(
                        if (worryLevel % divisibleByTest == 0) monkeyToThrowOnTrue else monkeyToThrowOnFalse,
                        worryLevel
                    )
                })

        fun acceptItem(itemWorry: Int) =
            copy(itemWorries = itemWorries + itemWorry)
    }

    data class ThrowTo(
        val monkeyIndex: Int,
        val itemWorry: Int
    )

    abstract class Operation {
        abstract operator fun invoke(value: Int): Int
    }

    class Plus(private val operand: Int?) : Operation() {
        override fun invoke(value: Int) = value + (operand ?: value)
    }

    class Multiply(private val operand: Int?) : Operation() {
        override fun invoke(value: Int) = value * (operand ?: value)
    }

    class Noop : Operation() {
        override fun invoke(value: Int) = value
    }

    private fun List<String>.toOperation(): Operation {
        val operator = this[0]
        val operand = if (this[1] == "old") null else this[1].toInt()
        return when (operator) {
            "*" -> Day11.Multiply(operand)
            "+" -> Day11.Plus(operand)
            else -> Day11.Noop()
        }
    }
}

