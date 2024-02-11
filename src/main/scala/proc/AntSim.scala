package proc

import proc.Utils.Functions

import scala.math.abs
import processing.core.{PApplet, PConstants}

/** Draws 3D spirals */
class AntSim extends PApplet {
    val ScreenDimension = 800
    var d: Int = 200
    val grid = new Grid(this, d, d)
    var activeSim: Boolean = false
    var brushSize = 2

    override def settings(): Unit = size(ScreenDimension, ScreenDimension)

    override def setup(): Unit = {
        frameRate(60)
        background(0)
        colorMode(PConstants.RGB, 255)
        rectMode(PConstants.CENTER)
    }


    def brush(cellType: CellType): Unit ={
        for (i <- -brushSize to brushSize; j <- -brushSize to brushSize if abs(i) + abs(j)< brushSize / 2){
            val newX = Functions.boundBy(0, grid.width - 1, (mouseX.toDouble / ScreenDimension * grid.width).toInt + i).toInt
            val newY = Functions.boundBy(0, grid.height - 1, (mouseY.toDouble / ScreenDimension * grid.height).toInt + j).toInt
            grid.cells(newX)(newY).changeType(cellType)
        }
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
        if (key == 'p' || key == ' '){
            if (activeSim) {
                activeSim = false
            } else {
                activeSim = true
            }
        }
        if ((key == '+' || key == '=') && brushSize < 20){
            brushSize += 2
        }
        if ((key == '-' || key == '_') && brushSize > 2){
            brushSize -= 2
        }

    }

    override def draw(): Unit = {
        noStroke()
        if (activeSim){
            grid.updateGrid()
        }
        grid.drawGrid()
    }
}

object AntSim {
    def main(args: Array[String]): Unit = PApplet.main("proc.AntSim")
}