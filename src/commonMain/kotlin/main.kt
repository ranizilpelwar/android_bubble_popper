
import com.soywiz.korge.Korge
import com.soywiz.korge.input.mouse
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Rectangle

suspend fun main() = Korge(width = 480, height = 640, title = "2048", bgcolor = RGBA(253, 247, 240)) {
    val font = resourcesVfs["clear_sans.fnt"].readBitmapFont()
    val cellSize = views.virtualWidth / 5.0
    val fieldSize = 50 + 4 * cellSize
    val leftIndent = (views.virtualWidth - fieldSize) / 2
    val topIndent = 150.0

    val bgField = roundRect(fieldSize, fieldSize, 5.0, fill = Colors["#b9aea0"]) {
        position(leftIndent, topIndent)
    }
    addChild(bgField)

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
