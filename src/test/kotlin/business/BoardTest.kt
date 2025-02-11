package business

import business.TestFixture.testCells
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

/**
 *  testCells
 *         0   1   2   3
 *
 *  0    | 1 | * | C | C |
 *  1    | C | C | C | C |
 *  2    | C | C | C | C |
 *  3    | C | C | C | C |
 *  4    | C | C | C | * |
 *  5    | C | C | * | C |
 */
class BoardTest {
    @Test
    fun `특정 위치에 지뢰이면 ture`() {
        // given
        val board = Board(testCells())

        // when
        val result = board.isMine(Point(0, 1))

        // then
        result shouldBe true
    }

    @Test
    fun `특정 위치에 지뢰가 아니면 false`() {
        // given
        val board = Board(testCells())

        // when
        val result = board.isMine(Point(0, 0))

        // then
        result shouldBe false
    }

    @Test
    fun `지뢰 위치를 open하면 실패 결과를 반환한다`() {
        // given
        val board = Board(testCells())

        // when
        val result = board.open(Point(0, 1))

        // then
        result shouldBe GameStatus.GAME_OVER
    }

    @Test
    fun `지뢰가 아니고 주변에 지뢰가 있는 위치를 open하면 해당 위치를 open하고 continue 결과를 반환한다`() {
        // given
        val board = Board(testCells())
        val targetPoint = Point(1, 0)

        // when
        val result = board.open(targetPoint)

        // then
        result shouldBe GameStatus.CONTINUE
    }

    /**
     *         0   1   2   3                             0   1   2   3
     *
     *  0    | C | * | C | C |                         | C | * | 1 | 0 |
     *  1    | C | C | C | C |                         | 1 | 1 | 1 | 0 |
     *  2    | C | C | C | C |      =>  open(2,1) =>   | 0 | 0 | 0 | 0 |
     *  3    | C | C | C | C |                         | 0 | 0 | 1 | 1 |
     *  4    | C | C | C | * |                         | 0 | 1 | 2 | * |
     *  5    | C | C | C | * |                         | 0 | 1 | * | C |
     */
    @Test
    fun `지뢰가 아니고 주변에 지뢰가 없는 위치를 open하면 해당 위치를 open하고 주변도 모두 open하며 continue 결과를 반환한다`() {
        // given
        val board = Board(testCells())
        val targetPoint = Point(2, 1)

        // when
        val result = board.open(targetPoint)

        // then
        result shouldBe GameStatus.CONTINUE
    }

    /**
     *         0   1   2   3                                   0   1   2   3
     *
     *  0    | C | * | C | C |                               | C | * | 1 | 0 |
     *  1    | C | C | C | C |                               | 1 | 1 | 1 | 0 |
     *  2    | C | C | C | C |      =>  open(2,1)(5,3)  =>   | 0 | 0 | 0 | 0 |
     *  3    | C | C | C | C |                               | 0 | 0 | 1 | 1 |
     *  4    | C | C | C | * |                               | 0 | 1 | 2 | * |
     *  5    | C | C | C | * |                               | 0 | 1 | * | 2 |
     */
    @Test
    fun `open 후 지뢰가 아닌 위치가 없으면 win 결과를 반환한다`() {
        // given
        val board = Board(testCells())
        val targetPoint = Point(2, 1)
        val targetPoint2 = Point(5, 3)

        // when
        board.open(targetPoint)
        val result = board.open(targetPoint2)

        // then
        result shouldBe GameStatus.WIN
    }
}
