
import com.soywiz.klock.*
import com.soywiz.korau.sound.readSound
import com.soywiz.korev.*
import com.soywiz.korge.*
import com.soywiz.korge.animate.*
import com.soywiz.korge.component.removeFromView
import com.soywiz.korge.input.*
import com.soywiz.korge.service.storage.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korio.async.*
import com.soywiz.korio.async.ObservableProperty
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.geom.vector.*
import com.soywiz.korma.interpolation.*
import kotlin.collections.set
import kotlin.properties.*
import kotlin.random.*

var cellSize: Double = 0.0
var fieldSize: Double = 0.0
var leftIndent: Double = 0.0
var topIndent: Double = 0.0
var playSounds: Boolean = true
var font: BitmapFont by Delegates.notNull()
var circles: MutableList<Unit> = mutableListOf()

suspend fun main() = Korge(width = 480, height = 640, title = "2048", bgcolor = RGBA(253, 247, 240)) {
    font = resourcesVfs["clear_sans.fnt"].readBitmapFont()
    val cellSize = views.virtualWidth / 5.0
    println("cellSize $cellSize")
    val fieldSize = 50 + 4 * cellSize
    val leftIndent = (views.virtualWidth - fieldSize) / 2
    val topIndent = 150.0
    println("leftIndent: $leftIndent")
    println("topIndent $topIndent")

    val touchBubbleWrapSound = resourcesVfs["touch_bubble_wrap.mp3"].readSound()
    val bubblePopSound = resourcesVfs["pop_bubble_wrap.mp3"].readSound()

    if (playSounds) {
        touchBubbleWrapSound.play()
    }



    val bgField = roundRect(fieldSize, fieldSize, 5.0, fill = ColorsTheme.BACKGROUND) {
        position(leftIndent, topIndent)
    }

    val bgLogo = roundRect(cellSize, cellSize, 5.0, fill = ColorsTheme.LOGO) {
        position(leftIndent, 30.0)
    }
    text("GO!", cellSize * 0.5, Colors.WHITE, font).centerOn(bgLogo)

    val bgBest = roundRect(cellSize * 1.5, cellSize * 0.8, 5.0, fill = ColorsTheme.BEST_SCORE) {
        alignRightToRightOf(bgField)
        alignTopToTopOf(bgLogo)
    }

    val bgScore = roundRect(cellSize * 1.5, cellSize * 0.8, 5.0, fill = ColorsTheme.SCORE) {
        alignRightToLeftOf(bgBest, 24)
        alignTopToTopOf(bgBest)
    }


    // 4 x 4 grid of bubbles
    graphics {
        fill(ColorsTheme.BUBBLE_ACTIVE) {
            for (i in 0..3) {
                for (j in 0..3) {
                    //roundRect(leftIndent + 10 + (10 + cellSize) * i, topIndent + 10 + (10 + cellSize) * j, cellSize, cellSize, 5.0)
                    //val drawnCircle = circle(leftIndent + 57 + (10 + cellSize) * i, topIndent + 57 + (10 + cellSize) * j, cellSize/2)
                    //circles.add(drawnCircle)
                }
            }
        }
    }

    var bubbleMap = bubbleMap(4, 4, cellSize, leftIndent, topIndent)

    fun restart() {
        println("restarted")
        bubbleMap.removeFromParent()
        bubbleMap = bubbleMap(4, 4, cellSize, leftIndent, topIndent)
    }
    //pop a bubble in the cell where the mouse was clicked
    onClick {
        val input = views.input
        val x = input.mouse.x
        val y = input.mouse.y
        println("mouse click x: $x")
        println("mouse click y: $y")

        var bubble = bubbleMap.getIntersectingBubbleOrNull(x, y)
        if (bubble != null && !bubble.isPopped()) {
            if (playSounds) {
                bubblePopSound.play()
            }
            bubble.popIt()
//            graphics {
//                fill(ColorsTheme.BUBBLE_INACTIVE) {
//                    circle(bubble.x, bubble.y, bubble.radius)
//                }
//            }
            bubble.removeFromParent()
            if (bubbleMap.areAllPopped()) {
                showGameOver() { restart() }

            }
        }
    }

    text("BEST", cellSize * 0.25, ColorsTheme.TEXT_LABEL, font) {
        centerXOn(bgBest)
        alignTopToTopOf(bgBest, 5.0)
    }
    text("0", cellSize * 0.5, ColorsTheme.TEXT_VALUE, font) {
        setTextBounds(Rectangle(0.0, 0.0, bgBest.width, cellSize - 24.0))
        alignment = TextAlignment.MIDDLE_CENTER
        alignTopToTopOf(bgBest, 12.0)
        centerXOn(bgBest)
    }
    text("SCORE", cellSize * 0.25, ColorsTheme.TEXT_LABEL, font) {
        centerXOn(bgScore)
        alignTopToTopOf(bgScore, 5.0)
    }
    text("0", cellSize * 0.5, Colors.WHITE, font) {
        setTextBounds(Rectangle(0.0, 0.0, bgScore.width, cellSize - 24.0))
        alignment = TextAlignment.MIDDLE_CENTER
        centerXOn(bgScore)
        alignTopToTopOf(bgScore, 12.0)
    }
}

fun columnX(number: Int) = leftIndent + 10 + (cellSize + 10) * number
fun rowY(number: Int) = topIndent + 10 + (cellSize + 10) * number



fun Stage.checkIfGameOver() {

}

fun Container.showGameOver(onRestart: () -> Unit) = container {
    fun restart() {
        this@container.removeFromParent()
        onRestart()
    }

    position(leftIndent, topIndent)

    roundRect(fieldSize, fieldSize, 5.0, fill = Colors["#FFFFFF33"])
    text("Game Over", 60.0, Colors.BLACK, font) {
        centerBetween(500.0, 500.0, fieldSize, fieldSize)
        y -= 60
    }
    uiText("Play again?", 120.0, 35.0) {
        centerBetween(500.0, 500.0, fieldSize, fieldSize)
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