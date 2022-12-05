import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class SolutionsTest : DescribeSpec({

    it("Day 01") {
        Day01().partOne() shouldBe "71502"
        Day01().partTwo() shouldBe "208191"
    }

    it("Day 02") {
        Day02().partOne() shouldBe "14264"
        Day02().partTwo() shouldBe "12382"
    }

    it("Day 03") {
        Day03().partOne() shouldBe "7737"
        Day03().partTwo() shouldBe "2697"
    }

    it("Day 04") {
        Day04().partOne() shouldBe "453"
        Day04().partTwo() shouldBe "919"
    }

    it("Day 05") {
        Day05().partOne() shouldBe "FWSHSPJWM"
//        Day04().partTwo() shouldBe "PWPWHGFZS"
    }
})