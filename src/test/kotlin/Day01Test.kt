import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day01Test : DescribeSpec({

    it("Day 01") {
        Day01().part1() shouldBeRight 71502
        Day01().part2() shouldBeRight 208191
    }

    it("Day 02") {
        Day02().part1() shouldBeRight 14264
        Day02().part2() shouldBeRight 12382
    }
})