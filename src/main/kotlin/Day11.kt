class Day11(testing: Boolean = false) : DaySolutions(11, testing) {
    override fun partOne(): SolutionResult =
        (0 until 20)
            .fold(parse(input)) { monkeys, _ -> monkeys.round { divideBy3(it) } }
            .productOf2MostActiveMonkeys()

    override fun partTwo(): SolutionResult =
        (0 until 10000)
            .fold(parse(input)) { monkeys, _ -> monkeys.round { modulus(monkeys.lmcOfDivisibleByValues)(it) } }
            .productOf2MostActiveMonkeys()

    private fun Monkeys.productOf2MostActiveMonkeys() =
        this.monkeys.asSequence()
            .map { it.inspectedItems }
            .sortedDescending()
            .take(2)
            .reduce { product, v -> product * v }
            .bind()

    private fun divideBy3(i: Long) = i / 3

    private fun modulus(m: Int) = { i: Long -> i % m }

    private fun parse(input: SolutionInput): Monkeys =
        Monkeys(input.chunked(7) { parseMonkey(it) }.toMutableList())

    private fun parseMonkey(monkeyLines: List<String>) =
        Monkey(
            parseLongs(monkeyLines[1], "Starting items:", ", "),
            parseStrings(monkeyLines[2], "Operation: new = old").toOperation(),
            parseInt(monkeyLines[3], "Test: divisible by"),
            parseInt(monkeyLines[4], "If true: throw to monkey"),
            parseInt(monkeyLines[5], "If false: throw to monkey"),
        )

    private fun parseInt(line: String, prefix: String, separator: String = " "): Int =
        parseLongs(line, prefix, separator).first().toInt()

    private fun parseLongs(line: String, prefix: String, separator: String = " "): List<Long> =
        parseStrings(line, prefix, separator).map { it.toLong() }

    private fun parseStrings(line: String, prefix: String, separator: String = " "): List<String> =
        line.trim().drop(prefix.length).trim().split(separator)

    class Monkeys(
        var monkeys: MutableList<Monkey>
    ) {
        fun round(worryLevelNormalizer: WorryLevelNormalizer): Monkeys {
            for (m in monkeys.indices) {
                val (monkey, throwTo) = monkeys[m].turn(worryLevelNormalizer)
                monkeys[m] = monkey
                throwTo.forEach {
                    monkeys[it.monkeyIndex] = monkeys[it.monkeyIndex].acceptItem(it.itemWorry)
                }
            }
            return this
        }

        val lmcOfDivisibleByValues get() = monkeys
            .map { it.divisibleBy }
            .reduce { p, v -> p * v }
    }

    data class Monkey(
        val itemWorries: List<Long>,
        val operation: Operation,
        val divisibleBy: Int,
        val monkeyToThrowOnTrue: Int,
        val monkeyToThrowOnFalse: Int,
        val inspectedItems: Long = 0
    ) {
        fun turn(worryLevelNormalizer: WorryLevelNormalizer): Pair<Monkey, List<ThrowTo>> =
            Pair(this.copy(itemWorries = emptyList(), inspectedItems = inspectedItems + itemWorries.size),
                itemWorries.map {
                    val worryLevel = worryLevelNormalizer(operation(it))
                    ThrowTo(
                        if (worryLevel % divisibleBy == 0L) monkeyToThrowOnTrue else monkeyToThrowOnFalse,
                        worryLevel
                    )
                })

        fun acceptItem(itemWorry: Long) =
            copy(itemWorries = itemWorries + itemWorry)
    }

    data class ThrowTo(
        val monkeyIndex: Int,
        val itemWorry: Long
    )

    interface Operation {
        operator fun invoke(value: Long): Long
    }

    class Plus(private val operand: Long?) : Operation {
        override fun invoke(value: Long) = value + (operand ?: value)
    }

    class Times(private val operand: Long?) : Operation {
        override fun invoke(value: Long) = value * (operand ?: value)
    }

    class Noop : Operation {
        override fun invoke(value: Long) = value
    }

    private fun List<String>.toOperation(): Operation {
        val operator = this[0]
        val operand = if (this[1] == "old") null else this[1].toLong()
        return when (operator) {
            "+" -> Plus(operand)
            "*" -> Times(operand)
            else -> Noop()
        }
    }
}

typealias WorryLevelNormalizer = (i: Long) -> Long

