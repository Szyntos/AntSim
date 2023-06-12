package proc


import processing.core.{PApplet, PConstants}
import Functions._

import proc.Direction.{D, DL, DR, L, R, U, UL, UR}

class Grid(val m: AntSim, var width: Int, var height: Int){
    val rand = scala.util.Random
    val cells = Array.ofDim[Cell](height, width)

    for (i <- 0 until width) {
        for (j <- 0 until height) {
            cells(i)(j) = new Cell(m, this, i, j, CellType.empty)
        }
    }



    def populateGridBy(cellType: CellType.CellType, where: Direction.Direction, count: Int): Unit = {
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
                        randomX = boundBy(0, width - 1, population(i).x + Direction.toXY(direction).x).toInt
                        randomY = boundBy(0, height - 1, population(i).y + Direction.toXY(direction).y).toInt
                        if (cells(randomX)(randomY).cell_type != cellType) {
                            population(populated) = cells(randomX)(randomY)
                            population(populated).cell_type = cellType
                            populated += 1
                        }
                    }
                    j += 1
                }
                i += 1
            }
        }
    }

    populateGridBy(CellType.food, Direction.UR, 500)
    populateGridBy(CellType.colony, Direction.DL, 300)
    def drawGrid(): Unit ={
        for (i <- 0 until width) {
            for (j <- 0 until height) {
                cells(i)(j).drawCell()
            }
        }
    }

}
