class Day10(testing: Boolean = false) : DaySolutions(10, testing) {
    override fun partOne(): SolutionResult =

        input.map { parse(it) }
            .fold(Cpu()) { cpu, instruction ->
                cpu.execute(instruction)
            }
            .signalStrengths.sum()
            .bind()

    private fun parse(line: String): Instruction =
        when {
            line.startsWith("noop") -> Instruction("noop")
            else -> Instruction("addx", line.split(" ")[1].toInt())
        }

    data class Instruction(
        val op: String,
        val value: Int? = null
    ) {
        fun cycles(): Int =
            when (op) {
                "noop" -> 1
                else -> 2
            }
    }

    data class Cpu(
        val xRegister: Int = 1,
        val programCounter: Int = 0,
        val signalStrengths: List<Int> = listOf()
    ) {
        fun execute(instruction: Instruction): Cpu {
            val newProgramCounter = programCounter + instruction.cycles()
            val newValue = xRegister + (instruction.value ?: 0)
            val signalEdge = (programCounter / SIGNAL_WINDOW) * SIGNAL_WINDOW + SIGNAL_START
            val newSignalStrengths =
                if ((programCounter < signalEdge) && (signalEdge <= newProgramCounter))
                    signalStrengths + signalEdge * xRegister
                else
                    signalStrengths
            return Cpu(newValue, newProgramCounter, newSignalStrengths)
        }
    }

    companion object {
        const val SIGNAL_START: Int = 20
        const val SIGNAL_WINDOW: Int = 40
    }
}
