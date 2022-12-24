import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class SegmentsUnionTest : DescribeSpec({

    it("second starts after") {
        listOf(Segment(2, 5), Segment(6, 7)).unionLength() shouldBe 6
    }
    it("seconds start when first ends") {
        listOf(Segment(2, 5), Segment(5, 7)).unionLength() shouldBe 6
    }
    it("second start before first ends") {
        listOf(Segment(2, 5), Segment(4, 7)).unionLength() shouldBe 6
    }
    it("several length 1") {
        listOf(Segment(2, 2), Segment(2, 2), Segment(3, 3)).unionLength() shouldBe 2
    }
    it("equal segments") {
        listOf(Segment(4, 6), Segment(4, 6)).unionLength() shouldBe 3
    }
    it("fully contains") {
        listOf(Segment(1, 10), Segment(4, 6)).unionLength()  shouldBe 10
    }
})