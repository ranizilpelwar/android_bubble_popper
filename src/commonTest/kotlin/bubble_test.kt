import com.soywiz.korge.tests.*
import kotlin.test.*

class BubbleTest : ViewsForTesting() {
    @Test
    fun testPoppingOfBubble() = viewsTest {
        val bubble = Bubble(1.0, 1.0, 1.0)
        bubble.popIt();

        assertTrue(bubble.isPopped());
    }

    @Test
    fun testDoesIntersectWithinCircle() = viewsTest {
        val bubble = Bubble(3.0, 3.0, 5.0);

        assertTrue(bubble.doesIntersectWith(4.0, 4.0))
    }

    @Test
    fun testDoesIntersectAtCircle() = viewsTest {
        val bubble = Bubble(3.0, 3.0, 5.0);

        assertTrue(bubble.doesIntersectWith(3.0, 8.0))
    }

    @Test
    fun testDoesNotIntersect() = viewsTest {
        val bubble = Bubble(3.0, 3.0, 1.0);

        assertFalse(bubble.doesIntersectWith(4.5, 4.0))
    }
}