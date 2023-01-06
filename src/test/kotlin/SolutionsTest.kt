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
        Day09().partTwo().intValue shouldBe 2651
    }

    it("Day 10") {
        Day10().partOne().intValue shouldBe 14540
        Day10().partTwo().value shouldBe """
            ####.#..#.####.####.####.#..#..##..####.
            #....#..#....#.#.......#.#..#.#..#....#.
            ###..####...#..###....#..####.#......#..
            #....#..#..#...#.....#...#..#.#.....#...
            #....#..#.#....#....#....#..#.#..#.#....
            ####.#..#.####.#....####.#..#..##..####.            
        """.trimIndent().trim()
    }

    it("Day 11") {
        Day11().partOne().intValue shouldBe 54054
        Day11().partTwo().longValue shouldBe 14314925001
    }

    it("Day 12") {
        Day12().partOne().intValue shouldBe 440
        Day12().partTwo().intValue shouldBe 439
    }

    it("Day 13") {
        Day13().partOne().intValue shouldBe 5882
        Day13().partTwo().intValue shouldBe 24948
    }

    it("Day 14") {
        Day14().partOne().intValue shouldBe 897
        Day14().partTwo().intValue shouldBe 26683
    }

    it("Day 15") {
        Day15().partOne().intValue shouldBe 6124805
        Day15().partTwo().longValue shouldBe 12555527364986
    }

    it("Day 16") {
        Day16(true).partOne().intValue shouldBe 1
    }

})
