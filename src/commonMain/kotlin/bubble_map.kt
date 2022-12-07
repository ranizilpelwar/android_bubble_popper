
class BubbleMap(
    private val rows: Int,
    private val columns: Int,
    private val cellSize: Double,
    private val leftIndent: Double,
    private val topIndent: Double
) {

    private var bubbles: MutableList<Bubble> = mutableListOf<Bubble>()

    init {
        for (i in 0 until columns) {
            for (j in 0 until rows) {
                bubbles.add(
                    Bubble(
                        leftIndent + 57 + (10 + cellSize) * i,
                        topIndent + 57 + (10 + cellSize) * j,
                        cellSize / 2
                    )
                )
            }
        }
    }

    fun getBubbles(): MutableList<Bubble> {
        return bubbles
    }

    fun getIntersectingBubbleOrNull(x: Double, y: Double): Bubble? {
        return bubbles.find { e -> e.doesIntersectWith(x, y)}
    }
}