import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec

class SolutionsTest : DescribeSpec({

    it("Day 01") {
        Day01().partOne() shouldBeRight 71502
        Day01().partTwo() shouldBeRight 208191
    }

    it("Day 02") {
        Day02().partOne() shouldBeRight 14264
        Day02().partTwo() shouldBeRight 12382
    }

    it("Day 03") {
        Day03().partOne() shouldBeRight 7737
        Day03().partTwo() shouldBeRight 2697
    }
})