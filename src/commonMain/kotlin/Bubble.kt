import java.lang.Math.sqrt

class Bubble(private val x: Double, private val y: Double, private val radius: Double) {
    // X and Y are the center of the circle

    private var isPopped: Boolean = false;

    fun popIt() {
        isPopped = true
    }

    fun doesIntersectWith(givenX: Double, givenY: Double): Boolean {
        val sum = (givenX - x) * (givenX - x) + (givenY - y) * (givenY - y)
        val distance = kotlin.math.sqrt(sum)
        return distance <= radius
    }

    fun isPopped(): Boolean {
        return isPopped
    }
}