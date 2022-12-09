import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class SolutionsTest : DescribeSpec({

    it("Day 01") {
        Day01().partOne().intValue shouldBe 71502
        Day01().partTwo().intValue shouldBe 208191
    }

    it("Day 02") {
        Day02().partOne().intValue shouldBe 14264
        Day02().partTwo().intValue shouldBe 12382
    }

    it("Day 03") {
        Day03().partOne().intValue shouldBe 7737
        Day03().partTwo().intValue shouldBe 2697
    }

    it("Day 04") {
        Day04().partOne().intValue shouldBe 453
        Day04().partTwo().intValue shouldBe 919
    }

    it("Day 05") {
        Day05().partOne().value shouldBe "FWSHSPJWM"
        Day05().partTwo().value shouldBe "PWPWHGFZS"
    }

    it("Day 06") {
        Day06().partOne().intValue shouldBe 1816
        Day06().partTwo().intValue shouldBe 2625
    }

    it("Day 07") {
        Day07().partOne().intValue shouldBe 1490523
        Day07().partTwo().intValue shouldBe 12390492
    }

    it("Day 08") {
        Day08().partOne().intValue shouldBe 1700
        Day08().partTwo().intValue shouldBe 470596
    }

    it("Day 09") {
        Day09().partOne().intValue shouldBe 6354
    }
})