
import com.soywiz.korau.sound.readSound
import com.soywiz.korev.Key
import com.soywiz.korge.Korge
import com.soywiz.korge.input.keys
import com.soywiz.korge.input.onClick
import com.soywiz.korge.input.onOut
import com.soywiz.korge.input.onOver
import com.soywiz.korge.ui.textColor
import com.soywiz.korge.ui.textFont
import com.soywiz.korge.ui.textSize
import com.soywiz.korge.ui.uiText
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korio.file.std.resourcesVfs
import kotlin.properties.Delegates

var cellSize: Double = 0.0
var fieldSize: Double = 0.0
var leftIndent: Double = 0.0
var topIndent: Double = 0.0
var playSounds: Boolean = true
var font: BitmapFont by Delegates.notNull()

suspend fun main() = Korge(width = 480, height = 640, title = "2048", bgcolor = RGBA(253, 247, 240)) {
    font = resourcesVfs["clear_sans.fnt"].readBitmapFont()
    val cellSize = views.virtualWidth / 5.0
    val fieldSize = 50 + 4 * cellSize
    val leftIndent = (views.virtualWidth - fieldSize) / 2
    val topIndent = 150.0

    val touchBubbleWrapSound = resourcesVfs["touch_bubble_wrap.mp3"].readSound()
    val bubblePopSound = resourcesVfs["pop_bubble_wrap.mp3"].readSound()

    if (playSounds) {
        touchBubbleWrapSound.play()
    }

    val bgLogo = roundRect(cellSize, cellSize, 5.0, fill = ColorsTheme.LOGO) {
        position(leftIndent, 30.0)
    }
    text("GO!", cellSize * 0.5, Colors.WHITE, font).centerOn(bgLogo)

    var bubbleMap = bubbleMap(4, 4, cellSize, leftIndent, topIndent)

    fun restart() {
        bubbleMap.removeFromParent()
        bubbleMap = bubbleMap(4, 4, cellSize, leftIndent, topIndent)
    }

    //pop a bubble in the cell where the mouse was clicked
    onClick {
        val input = views.input
        val x = input.mouse.x
        val y = input.mouse.y

        var bubble = bubbleMap.getIntersectingBubbleOrNull(x, y)
        if (bubble != null && !bubble.isPopped()) {
            if (playSounds) {
                bubblePopSound.play()
            }
            bubble.popIt()
            bubble.removeFromParent()
            if (bubbleMap.areAllPopped()) {
                showGameOver() { restart() }

            }
        }
    }
}

fun Container.showGameOver(onRestart: () -> Unit) = container {
    fun restart() {
        this@container.removeFromParent()
        onRestart()
    }

    position(leftIndent, topIndent)

    roundRect(fieldSize, fieldSize, 5.0, fill = Colors["#FFFFFF33"])
    text("Game Over", 60.0, Colors.BLACK, font) {
        centerBetween(550.0, 200.0, fieldSize, fieldSize)
        y -= 60
    }
    uiText("Play again?", 120.0, 35.0) {
        centerBetween(530.0, 180.0, fieldSize, fieldSize)
        y += 20
        textSize = 40.0
        textFont = font
        textColor = RGBA(0, 0, 0)
        onOver { textColor = RGBA(90, 90, 90) }
        onOut { textColor = RGBA(0, 0, 0) }
        onClick { restart() }
    }

    keys.down {
        when (it.key) {
            Key.ENTER, Key.SPACE -> restart()
            else -> Unit
        }
    }
}