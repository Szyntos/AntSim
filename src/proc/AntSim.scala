package proc

import scala.math.{Pi, cos, sin, sqrt}
import scala.util.Random
import processing.core.{PApplet, PConstants}
import processing.event.KeyEvent

/** Draws 3D spirals */
class AntSim extends ScalaProcessingApplet {
    val ScreenDimension = 1000
    var d: Int = 200
    val grid = new Grid(this, d, d)

    override def settings(): Unit = size(ScreenDimension, ScreenDimension)

    override def setup(): Unit = {
        frameRate(60)
        background(0)
        colorMode(PConstants.RGB, 255)
        rectMode(PConstants.CENTER)
    }

    def dist(x1: Int, y1: Int, x2: Int, y2: Int): Float = {
        sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)).toFloat
    }

    override def mousePressed(): Unit = {
        val newX = Functions.boundBy(0, grid.width - 1, (mouseX.toDouble/ScreenDimension * grid.width).toInt).toInt
        val newY = Functions.boundBy(0, grid.height - 1, (mouseY.toDouble/ScreenDimension * grid.height).toInt).toInt
        grid.cells(newX)(newY).changeType(CellType.obstacle)
    }

    override def mouseDragged(): Unit = {
        val newX = Functions.boundBy(0, grid.width - 1, (mouseX.toDouble / ScreenDimension * grid.width).toInt).toInt
        val newY = Functions.boundBy(0, grid.height - 1, (mouseY.toDouble / ScreenDimension * grid.height).toInt).toInt
        grid.cells(newX)(newY).changeType(CellType.obstacle)
    }

    override def draw(): Unit = {
        noStroke()
//        background(0)
        grid.updateGrid()
        grid.drawGrid()
    }
}

object AntSim {
    def main(args: Array[String]): Unit = PApplet.main("proc.AntSim")
}