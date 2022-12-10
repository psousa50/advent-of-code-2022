class Day10(testing: Boolean = false) : DaySolutions(10, testing) {
    override fun partOne(): SolutionResult =
        input
            .map { parse(it) }
            .fold(VideoSystem()) { system, instruction -> system.run(instruction) }
            .cpu.signalStrengths.sum()
            .bind()

    override fun partTwo(): SolutionResult =
        input
            .map { parse(it) }
            .fold(VideoSystem()) { system, instruction -> system.run(instruction) }
            .crt.display()
            .bind()

    private fun parse(line: String): Instruction =
        when {
            line.startsWith("noop") -> Instruction(OP_NOOP)
            else -> Instruction(OP_ADDX, line.split(" ")[1].toInt())
        }

    data class Instruction(
        val op: String,
        val value: Int? = null
    ) {
        fun cycles(): Int =
            when (op) {
                OP_NOOP -> 1
                else -> 2
            }
    }

    data class VideoSystem(
        val cpu: Cpu = Cpu(),
        val crt: Crt = Crt()
    ) {
        fun run(instruction: Instruction) =
            VideoSystem(
                cpu.execute(instruction),
                (0 until instruction.cycles())
                    .fold(crt) { crt, _ -> crt.drawPixel(cpu.xRegister) }
            )
    }

    data class Cpu(
        val xRegister: Int = 1,
        val programCounter: Int = 0,
        val signalStrengths: List<Int> = listOf(),
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

    data class Crt(val pixels: String = "") {
        fun display() = pixels.chunked(DISPLAY_WIDTH).joinToString(System.lineSeparator())
        fun drawPixel(spritePosition: Int): Crt {
            val beamPosition = pixels.length % DISPLAY_WIDTH
            val pixel = if (beamPosition in spritePosition - 1..spritePosition + 1) "#" else "."
            return Crt(pixels + pixel)
        }
    }

    companion object {
        const val SIGNAL_START: Int = 20
        const val SIGNAL_WINDOW: Int = 40
        const val DISPLAY_WIDTH: Int = 40
        const val OP_NOOP = "noop"
        const val OP_ADDX = "addx"
    }
}
