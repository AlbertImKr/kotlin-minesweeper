package business

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PointTest {

    @ParameterizedTest
    @CsvSource(value = ["1,1,false", "1,2,true", "0,1,true", "0,0,true", "0,2,true", "1,3,false"])
    fun `주변의 포인트 인지 확인한다`(x: Int, y: Int, expected: Boolean) {
        // given
        val point = Point(1, 1)

        // when
        val actual = point.isAround(Point(x, y))

        // then
        actual shouldBe expected
    }

    @Test
    fun `주변의 포인트를 생성한다`() {
        // given
        val point = Point(1, 1)

        // when
        val actual = point.aroundPoints()

        // then
        actual shouldBe listOf(
            Point(0, 0),
            Point(0, 1),
            Point(0, 2),
            Point(1, 0),
            Point(1, 2),
            Point(2, 0),
            Point(2, 1),
            Point(2, 2)
        )
    }
}
