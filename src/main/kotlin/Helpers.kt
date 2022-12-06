fun <T> List<String>.splitByBlankLine(process: (lines: List<List<String>>) -> T): T {
    val emptyLineGroups: List<List<String>> = listOf(listOf())
    val lineGroups = this.fold(emptyLineGroups) { lists, line ->
        when {
            line.isEmpty() -> lists + listOf(listOf())
            else -> lists.dropLast(1) + listOf(lists.last() + line)
        }
    }
    return process(lineGroups)
}

