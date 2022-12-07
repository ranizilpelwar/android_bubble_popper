import com.soywiz.korge.tests.*
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