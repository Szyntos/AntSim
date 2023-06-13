package proc

import scala.util.Random

class Cell(val m: AntSim, val g: Grid, val x: Int, val y: Int, var cell_type: CellType.CellType) {
    var color: Color = Config.default_color
    var life_time: Float = 100
    var prevR: Float = 0
    var prevG: Float = 0
    var prevB: Float = 0
    changeColor()
    def changeColor(): Unit = {
        cell_type match {
            case CellType.empty => color = new Color(200, 200, 200)
            case CellType.pheromone_red => color = new Color(200, 30, 30)
            case CellType.pheromone_blue => color = new Color(30, 30, 200)
            case CellType.food => color = new Color(70, 200, 0)
            case CellType.colony => color = new Color(200, 100, 30)
            case CellType.obstacle => color = new Color(140, 100, 50)
            case _ => color = new Color(0, 0, 0)
        }
        prevR = color.r
        prevG = color.g
        prevB = color.b
    }

    def changeType(newCellType: CellType.CellType): Unit ={
        if (newCellType == CellType.pheromone_blue || newCellType == CellType.pheromone_red) {

            changeColor()
        }
        life_time = 100
        cell_type = newCellType
        changeColor()

    }

    def updateCell(): Unit = {
        if (life_time < 0){
            life_time = 1000
            changeType(CellType.empty)
        }
        if (cell_type == CellType.pheromone_blue || cell_type == CellType.pheromone_red){
            var r: Float = scala.util.Random.nextFloat() % 3f
            life_time -= r
            prevR = Functions.boundBy(0, 200, prevR + (r / 170))
            prevG = Functions.boundBy(0, 200, prevG + 1)
            prevB = Functions.boundBy(0, 200, prevB + 1)
            color.r = Functions.boundBy(0, 200, prevR).toInt
            color.g = Functions.boundBy(0, 200, prevG).toInt
            color.b = Functions.boundBy(0, 200, prevB).toInt
        }
    }

    def drawCell(): Unit = {
//        m.stroke(0)
        m.fill(color.r, color.g, color.b)
        m.square(x*(m.ScreenDimension/g.width) + (m.ScreenDimension/g.width)/2,
            y*(m.ScreenDimension/g.height) + (m.ScreenDimension/g.height)/2,
            m.ScreenDimension/g.width)
//        m.circle(x*(m.ScreenDimension/g.width) + (m.ScreenDimension/g.width)/2,
//            y*(m.ScreenDimension/g.height) + (m.ScreenDimension/g.height)/2,
//            m.ScreenDimension/g.width+20)
        m.fill(Config.default_color.r, Config.default_color.g, Config.default_color.b)
        m.noStroke()
    }
}
