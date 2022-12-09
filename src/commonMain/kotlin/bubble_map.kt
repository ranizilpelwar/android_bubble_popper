import com.soywiz.korge.view.*
import com.soywiz.korma.geom.vector.circle

fun Container.bubbleMap(rows: Int, columns: Int, cellSize: Double, leftIndent: Double, topIndent: Double) =
    BubbleMap(rows, columns, cellSize, leftIndent, topIndent).addTo(this)

class BubbleMap(
    private val rows: Int,
    private val columns: Int,
    private val cellSize: Double,
    private val leftIndent: Double,
    private val topIndent: Double
) : Container() {

    private var bubbles: MutableList<Bubble> = mutableListOf()

    init {
        for (i in 0 until columns) {
            for (j in 0 until rows) {
                bubbles.add(
                    bubble(
                        leftIndent + 57 + (10 + cellSize) * i,
                        topIndent + 57 + (10 + cellSize) * j,
                        cellSize / 2
                    ).position(leftIndent + 7 + (10 + cellSize) * i, topIndent + 7 + (10 + cellSize) * j)
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

    fun areAllPopped(): Boolean {
        return false
    }

}