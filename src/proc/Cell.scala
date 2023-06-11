package proc

import scala.util.Random

class Cell(val m: AntSim, val g: Grid, val x: Int, val y: Int, var cell_type: CellType.CellType) {
    var color: Color = Config.default_color

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
    }

    def drawCell(): Unit = {
        m.stroke(0)
        changeColor()
        m.fill(color.r, color.g, color.b)
        m.square(x*(m.ScreenDimension/g.width),
            y*(m.ScreenDimension/g.width),
            m.ScreenDimension/g.width)
        m.fill(Config.default_color.r, Config.default_color.g, Config.default_color.b)
        m.noStroke()
    }
}
