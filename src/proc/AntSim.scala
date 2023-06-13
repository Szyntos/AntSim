package proc

import scala.math.{Pi, cos, sin, sqrt}
import scala.util.Random
import processing.core.{PApplet, PConstants}
import processing.event.KeyEvent

/** Draws 3D spirals */
class AntSim extends PApplet {
    val ScreenDimension = 800
    var d: Int = 200
    val grid = new Grid(this, d, d)
    var activeSim: Boolean = false

    override def settings(): Unit = size(ScreenDimension, ScreenDimension)

    override def setup(): Unit = {
        frameRate(60)
        background(0)
        colorMode(PConstants.RGB, 255)
        rectMode(PConstants.CENTER)
    }


    def brush(cellType: CellType.CellType): Unit ={
        var newX = Functions.boundBy(0, grid.width - 1, (mouseX.toDouble / ScreenDimension * grid.width).toInt).toInt
        var newY = Functions.boundBy(0, grid.height - 1, (mouseY.toDouble / ScreenDimension * grid.height).toInt).toInt
        grid.cells(newX)(newY).changeType(cellType)
        newX = Functions.boundBy(0, grid.width - 1, (mouseX.toDouble / ScreenDimension * grid.width).toInt + 1).toInt
        newY = Functions.boundBy(0, grid.height - 1, (mouseY.toDouble / ScreenDimension * grid.height).toInt).toInt
        grid.cells(newX)(newY).changeType(cellType)
        newX = Functions.boundBy(0, grid.width - 1, (mouseX.toDouble / ScreenDimension * grid.width).toInt - 1).toInt
        newY = Functions.boundBy(0, grid.height - 1, (mouseY.toDouble / ScreenDimension * grid.height).toInt).toInt
        grid.cells(newX)(newY).changeType(cellType)
        newX = Functions.boundBy(0, grid.width - 1, (mouseX.toDouble / ScreenDimension * grid.width).toInt).toInt
        newY = Functions.boundBy(0, grid.height - 1, (mouseY.toDouble / ScreenDimension * grid.height).toInt + 1).toInt
        grid.cells(newX)(newY).changeType(cellType)
        newX = Functions.boundBy(0, grid.width - 1, (mouseX.toDouble / ScreenDimension * grid.width).toInt).toInt
        newY = Functions.boundBy(0, grid.height - 1, (mouseY.toDouble / ScreenDimension * grid.height).toInt - 1).toInt
        grid.cells(newX)(newY).changeType(cellType)
    }

    def mouseHandler(): Unit = {
        if (mouseButton == PConstants.LEFT) {
            brush(CellType.obstacle)
        } else if (mouseButton == PConstants.CENTER) {
            brush(CellType.food)
        } else {
            brush(CellType.empty)
        }
    }

    override def mousePressed(): Unit = {
        mouseHandler()
    }

    override def mouseDragged(): Unit = {
        mouseHandler()
    }

    override def keyPressed(): Unit = {
        if (activeSim){
            activeSim = false
        }else{
            activeSim = true
        }
    }

    override def draw(): Unit = {
        noStroke()
//        background(0)
        if (activeSim){
            grid.updateGrid()
        }
        grid.drawGrid()
    }
}

object AntSim {
    def main(args: Array[String]): Unit = PApplet.main("proc.AntSim")
}