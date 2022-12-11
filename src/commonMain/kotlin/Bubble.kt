import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addTo
import com.soywiz.korge.view.circle
import com.soywiz.korim.color.Colors

fun Container.bubble(centerX: Double, centerY: Double, radius: Double) = Bubble(centerX, centerY, radius).addTo(this)
class Bubble(val centerX: Double, val centerY: Double, val radius: Double): Container() {
    // X and Y are the center of the circle

    private var isPopped: Boolean = false;

    init {
        circle(radius, Colors.BLUE)
    }
    fun popIt() {
        isPopped = true
    }

    fun doesIntersectWith(givenX: Double, givenY: Double): Boolean {
        val sum = (givenX - centerX) * (givenX - centerX) + (givenY - centerY) * (givenY - centerY)
        val distance = kotlin.math.sqrt(sum)
        return distance <= radius
    }

    fun isPopped(): Boolean {
        return isPopped
    }
}