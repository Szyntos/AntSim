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
        val populated = Array.ofDim[Cell](count)
        var randomX: Int = 0
        var randomY: Int = 0
        where match {
            case U =>
                randomX = rand.between(boundBy(0, width - 1, width/2-10),
                    boundBy(0, width - 1, width/2+10)).toInt
                randomY = rand.between(0, boundBy(0, height - 1, 20)).toInt
            case D =>
                randomX = rand.between(boundBy(0, width - 1, width / 2 - 10),
                    boundBy(0, width - 1, width / 2 + 10)).toInt
                randomY = rand.between(boundBy(0, height - 1, height - 20), height).toInt
            case R =>
                randomX = rand.between(boundBy(0, width - 1, width - 20), width).toInt
                randomY = rand.between(boundBy(0, height - 1, height / 2 - 10),
                    boundBy(0, height - 1, height / 2 + 10)).toInt
            case L =>
                randomX = rand.between(0, boundBy(0, width - 1, 20)).toInt
                randomY = rand.between(boundBy(0, height - 1, height / 2 - 10),
                    boundBy(0, height - 1, height / 2 + 10)).toInt
            case UR =>
                randomX = rand.between(boundBy(0, width - 1, width - 20), width).toInt
                randomY = rand.between(0, boundBy(0, height - 1, 20)).toInt
            case UL =>
                randomX = rand.between(0, boundBy(0, width - 1, 20)).toInt
                randomY = rand.between(0, boundBy(0, height - 1, 20)).toInt
            case DR =>
                randomX = rand.between(boundBy(0, width - 1, width - 20), width).toInt
                randomY = rand.between(boundBy(0, height - 1, height - 20), height).toInt
            case DL =>
                randomX = rand.between(0, boundBy(0, width - 1, 20)).toInt
                randomY = rand.between(boundBy(0, height - 1, height - 20), height).toInt
            case _ =>
                randomX = 0
                randomY = 0
        }

        var direction: Direction.Direction = Direction.U
        var i: Int = 0
        while (i < count) {
            for (j <- 0 until 5) {
                if (i != count) {
                    direction = Direction(rand.nextInt(Direction.maxId))
                    randomX = boundBy(0, width - 1, randomX + Direction.toXY(direction).x).toInt
                    randomY = boundBy(0, height - 1, randomY + Direction.toXY(direction).y).toInt
                    if (cells(randomX)(randomY).cell_type != cellType) {
                        populated(i) = cells(randomX)(randomY)
                        populated(i).cell_type = cellType
                        i += 1
                    }
                }
            }
        }
    }

    populateGridBy(CellType.food, Direction.UR, 300)
    populateGridBy(CellType.colony, Direction.DL, 300)
    def drawGrid(): Unit ={
        for (i <- 0 until width) {
            for (j <- 0 until height) {
                cells(i)(j).drawCell()
            }
        }
    }

}
