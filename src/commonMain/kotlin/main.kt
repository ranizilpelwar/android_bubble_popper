
import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.*
import com.soywiz.korge.animate.*
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

suspend fun main() = Korge(width = 480, height = 640, title = "2048", bgcolor = RGBA(253, 247, 240)) {
    val font = resourcesVfs["clear_sans.fnt"].readBitmapFont()
    val cellSize = views.virtualWidth / 5.0
    println("cellSize $cellSize")
    val fieldSize = 50 + 4 * cellSize
    val leftIndent = (views.virtualWidth - fieldSize) / 2
    val topIndent = 150.0
    println("leftIndent: $leftIndent")
    println("topIndent $topIndent")

    val bubbleMap = BubbleMap(4, 4, cellSize, leftIndent, topIndent)

    val bgField = roundRect(fieldSize, fieldSize, 5.0, fill = Colors["#b9aea0"]) {
        position(leftIndent, topIndent)
    }

    val bgLogo = roundRect(cellSize, cellSize, 5.0, fill = Colors["#edc403"]) {
        position(leftIndent, 30.0)
    }
    text("GO!", cellSize * 0.5, Colors.WHITE, font).centerOn(bgLogo)

    val bgBest = roundRect(cellSize * 1.5, cellSize * 0.8, 5.0, fill = Colors["#bbae9e"]) {
        alignRightToRightOf(bgField)
        alignTopToTopOf(bgLogo)
    }

    val bgScore = roundRect(cellSize * 1.5, cellSize * 0.8, 5.0, fill = Colors["#bbae9e"]) {
        alignRightToLeftOf(bgBest, 24)
        alignTopToTopOf(bgBest)
    }

    // 4 x 4 grid
    graphics {
        fill(Colors["#3f50a6"]) {
            for (i in 0..3) {
                for (j in 0..3) {
                    //roundRect(leftIndent + 10 + (10 + cellSize) * i, topIndent + 10 + (10 + cellSize) * j, cellSize, cellSize, 5.0)
                    circle(leftIndent + 57 + (10 + cellSize) * i, topIndent + 57 + (10 + cellSize) * j, cellSize/2)
                }
            }
        }
    }

    //pop a bubble in the cell where the mouse was clicked
    onClick {
        val input = views.input
        val x = input.mouse.x
        val y = input.mouse.y
        println("mouse click x: $x")
        println("mouse click y: $y")
        var bubble = bubbleMap.getIntersectingBubbleOrNull(x, y)
        if (bubble != null) {
            graphics {
                fill(Colors["#b9aea0"]) {
                    circle(bubble.x, bubble.y, bubble.radius)
                }
            }
        }
    }

    text("BEST", cellSize * 0.25, RGBA(239, 226, 210), font) {
        centerXOn(bgBest)
        alignTopToTopOf(bgBest, 5.0)
    }
    text("0", cellSize * 0.5, Colors.WHITE, font) {
        setTextBounds(Rectangle(0.0, 0.0, bgBest.width, cellSize - 24.0))
        alignment = TextAlignment.MIDDLE_CENTER
        alignTopToTopOf(bgBest, 12.0)
        centerXOn(bgBest)
    }
    text("SCORE", cellSize * 0.25, RGBA(239, 226, 210), font) {
        centerXOn(bgScore)
        alignTopToTopOf(bgScore, 5.0)
    }
    text("0", cellSize * 0.5, Colors.WHITE, font) {
        setTextBounds(Rectangle(0.0, 0.0, bgScore.width, cellSize - 24.0))
        alignment = TextAlignment.MIDDLE_CENTER
        centerXOn(bgScore)
        alignTopToTopOf(bgScore, 12.0)
    }

    root.mouse.click {
        println("Mouse clicked!!")

    }
}

fun columnX(number: Int) = leftIndent + 10 + (cellSize + 10) * number
fun rowY(number: Int) = topIndent + 10 + (cellSize + 10) * number

