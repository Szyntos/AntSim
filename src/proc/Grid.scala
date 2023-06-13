package proc


import processing.core.{PApplet, PConstants}
import Functions._
import proc.Direction.{D, DL, DR, L, R, U, UL, UR}

import scala.util.Random

class Grid(val m: AntSim, var width: Int, var height: Int){
    val rand: Random.type = scala.util.Random
    val cells: Array[Array[Cell]] = Array.ofDim[Cell](height, width)
    val antCount: Int = 1000
    val ants: Array[Ant] = Array.ofDim[Ant](antCount)
    for (i <- 0 until width) {
        for (j <- 0 until height) {
            cells(i)(j) = new Cell(m, this, i, j, CellType.empty)
        }
    }
    populateGridBy(CellType.food, Direction.C, 3000)
    populateGridBy(CellType.colony, Direction.UL, 1000)
    for (j <- 0 until antCount) {
        ants(j) = new Ant(m, this, 20, 20)
    }




    def populateGridBy(cellType: CellType.CellType, where: Direction.Direction, n: Int): Unit = {
        val count: Int = boundBy(0, width*height-1, n).toInt
        val population = Array.ofDim[Cell](count)
        var populated: Int = 0
        var randomX: Int = 0
        var randomY: Int = 0
        val wiggleRoom: Int = 40
        where match {
            case U =>
                randomX = rand.between(boundBy(0, width - 1, width/2-wiggleRoom/2),
                    boundBy(0, width - 1, width/2+wiggleRoom/2)).toInt
                randomY = rand.between(0, boundBy(0, height - 1, wiggleRoom)).toInt
            case D =>
                randomX = rand.between(boundBy(0, width - 1, width / 2 - wiggleRoom/2),
                    boundBy(0, width - 1, width / 2 + wiggleRoom/2)).toInt
                randomY = rand.between(boundBy(0, height - 1, height - wiggleRoom), height).toInt
            case R =>
                randomX = rand.between(boundBy(0, width - 1, width - wiggleRoom), width).toInt
                randomY = rand.between(boundBy(0, height - 1, height / 2 - wiggleRoom/2),
                    boundBy(0, height - 1, height / 2 + wiggleRoom/2)).toInt
            case L =>
                randomX = rand.between(0, boundBy(0, width - 1, wiggleRoom)).toInt
                randomY = rand.between(boundBy(0, height - 1, height / 2 - wiggleRoom/2),
                    boundBy(0, height - 1, height / 2 + wiggleRoom/2)).toInt
            case UR =>
                randomX = rand.between(boundBy(0, width - 1, width - wiggleRoom), width).toInt
                randomY = rand.between(0, boundBy(0, height - 1, wiggleRoom)).toInt
            case UL =>
                randomX = rand.between(0, boundBy(0, width - 1, wiggleRoom)).toInt
                randomY = rand.between(0, boundBy(0, height - 1, wiggleRoom)).toInt
            case DR =>
                randomX = rand.between(boundBy(0, width - 1, width - wiggleRoom), width).toInt
                randomY = rand.between(boundBy(0, height - 1, height - wiggleRoom), height).toInt
            case DL =>
                randomX = rand.between(0, boundBy(0, width - 1, wiggleRoom)).toInt
                randomY = rand.between(boundBy(0, height - 1, height - wiggleRoom), height).toInt
            case Direction.C =>
                randomX = rand.between(boundBy(0, width - 1, width/2 - wiggleRoom),
                    boundBy(0, width - 1, width/2 + wiggleRoom)).toInt
                randomY = rand.between(boundBy(0, height - 1, height/2 - wiggleRoom),
                    boundBy(0, height - 1, height/2 + wiggleRoom)).toInt
            case _ =>
                randomX = 0
                randomY = 0
        }
        for (i <- 0 until count) {
            population(i) = new Cell(m, this, randomX, randomY, CellType.empty)
        }

        var direction: Direction.Direction = Direction.U
        while (populated < count) {
            var i: Int = 0
            while (i < count){
                var j: Int = 0
                while (j < 5) {
                    if (populated != count) {
                        direction = Direction(rand.nextInt(Direction.maxId))
                        randomX = boundBy(0, width - 1, population(i).x + Direction.toVector(direction).x).toInt
                        randomY = boundBy(0, height - 1, population(i).y + Direction.toVector(direction).y).toInt
                        if (cells(randomX)(randomY).cell_type != cellType) {
                            population(populated) = cells(randomX)(randomY)
                            population(populated).changeType(cellType)
                            populated += 1
                        }
                    }
                    j += 1
                }
                i += 1
            }
        }
    }


    def updateGrid(): Unit ={
        for (j <- 0 until antCount) {
            ants(j).move()
        }
        for (i <- 0 until width) {
            for (j <- 0 until height) {
                cells(i)(j).updateCell()
            }
        }
    }
    def drawGrid(): Unit ={
        for (i <- 0 until width) {
            for (j <- 0 until height) {
                cells(i)(j).drawCell()
            }
        }
        for (j <- 0 until antCount) {
            ants(j).drawAnt()
        }
    }

}
