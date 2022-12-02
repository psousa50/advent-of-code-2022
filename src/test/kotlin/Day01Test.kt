import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day01Test : DescribeSpec({

    it("Day 01") {
        Day01(testing = true).part1() shouldBe "24000"
        Day01().part1() shouldBe "71502"
        Day01().part2() shouldBe "208191"
    }

    it("Day 02") {
        Day02(testing = true).part1() shouldBe "15"
        Day02().part1() shouldBe "14264"
    }
})