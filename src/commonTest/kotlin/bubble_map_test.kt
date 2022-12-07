import com.soywiz.klock.*
import com.soywiz.korge.input.*
import com.soywiz.korge.tests.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korma.geom.*
import kotlin.test.*

class BubbleMapTest : ViewsForTesting() {
    @Test
    fun testGetBubbles() = viewsTest {
        val bubbleMap = BubbleMap(4, 4, 5.0, 10.0, 10.0)
        val result = bubbleMap.getBubbles()

        assertEquals(16, result.size)
    }

    @Test
    fun testGetIntersectingBubbleHasResultAsFirstBubbleInGrid() {
        val bubbleMap = BubbleMap(4, 4, 96.0, 23.0, 150.0)
        val result = bubbleMap.getIntersectingBubbleOrNull(78.0, 206.0)

        assertNotNull(result)
        assertEquals(result.x, 23.0 + 57.0)
        assertEquals(result.y, 150.0 + 57.0)
    }

    @Test
    fun testGetIntersectingBubbleHasResultAsLatterBubbleInGrid() {
        val bubbleMap = BubbleMap(4, 4, 96.0, 23.0, 150.0)
        val result = bubbleMap.getIntersectingBubbleOrNull(285.0, 334.0)

        assertNotNull(result)
        assertEquals(result.x, 23.0 + 57.0 + (10 + 96) * 2)
        assertEquals(result.y, 150.0 + 57.0 + (10 + 96) * 1)
    }
}