import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec

class SolutionsTest : DescribeSpec({

    it("Day 01") {
        AoCRunner().runPart(1, DayPart.ONE) shouldBeRight 71502
        AoCRunner().runPart(1, DayPart.TWO) shouldBeRight 208191
    }

    it("Day 02") {
        AoCRunner().runPart(2, DayPart.ONE) shouldBeRight 14264
        AoCRunner().runPart(2, DayPart.TWO) shouldBeRight 12382
    }

    it("Day 03") {
        AoCRunner().runPart(3, DayPart.ONE) shouldBeRight 7737
    }
})