class Day07(testing: Boolean = false) : DaySolutions(7, testing) {
    override fun partOne(): SolutionResult =
        input
            .map { parse(it) }
            .fold(FileSystem()) { fs, item -> process(fs, item) }
            .allItems()
            .filter { it.name != "/" }
            .filter { it is Directory }
            .filter { it.size <= 100000 }
            .sumOf { it.size }
            .bind()

    override fun partTwo(): SolutionResult {
        val fs = input
            .map { parse(it) }
            .fold(FileSystem()) { fs, item -> process(fs, item) }

        val spaceNeeded = 30000000- (70000000 - fs.totalSpace)

        return fs
            .allItems()
            .filter { it is Directory }
            .filter { it.size >= spaceNeeded }
            .toList().minByOrNull { it.size }!!
            .size
            .bind()

    }

    private fun process(fileSystem: FileSystem, item: ParsedItem): FileSystem {
        when (item) {
            is CdCommand -> fileSystem.cd(item.directory)
            is FileSystemItem -> fileSystem.add(item)
        }
        return fileSystem
    }

    private fun parse(line: String): ParsedItem {
        return when {
            line.startsWith("$ cd") -> CdCommand(line.split(" ")[2])
            line.startsWith("dir") -> Directory(line.split(" ")[1])
            line[0].isDigit() -> File(line.split(" ")[1], line.split(" ")[0].toInt())
            else -> Ignore()
        }
    }
}

interface ParsedItem
interface Command

class CdCommand(val directory: String) : Command, ParsedItem
class Ignore : Command, ParsedItem


interface FileSystemItem : ParsedItem {
    val name: String
    val size: Int
}

data class Directory(
    override val name: String,
    var parent: Directory? = null,
    val items: MutableList<FileSystemItem> = mutableListOf()
) : FileSystemItem {
    override val size get() = items.sumOf { it.size }
    fun add(item: FileSystemItem) {
        if (item is Directory) item.parent = this
        items.add(item)
    }

    override fun toString() = name
}

data class File(override val name: String, override val size: Int) : FileSystemItem {
    override fun toString() = "${name}-${size}"
}

class FileSystem {
    private val root: Directory = Directory("/")
    private var currentDirectory: Directory = root
    val totalSpace get() = root.size

    fun cd(dir: String) {
        currentDirectory = when (dir) {
            "/" -> root
            ".." -> currentDirectory?.parent ?: root
            else -> currentDirectory.items.filterIsInstance<Directory>().first { it.name == dir }
        }
    }

    fun add(item: FileSystemItem) {
        currentDirectory.add(item)
    }

    fun allItems() = traverse(root)

    private fun traverse(dir: Directory): Sequence<FileSystemItem> =
        sequence {
            for (i in dir.items) {
                yield(i)
                if (i is Directory) {
                    yieldAll(traverse(i))
                }
            }
        }
}