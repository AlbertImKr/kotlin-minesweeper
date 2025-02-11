package business

class BoardInfo(private val height: Int, val width: Int, val mineCount: Int) {
    val size: Int
        get() = height * width

    fun getAllPoints(): Points = Points(generateAllPoints())

    private fun generateAllPoints() = sequence {
        for (x in START_INDEX until height) for (y in START_INDEX until width) yield(
            Point(x, y)
        )
    }.toList()

    companion object {
        private const val START_INDEX = 0
    }
}
