package proc

import proc.Utils.Color

case class CellType(name: String, color: Color)

object CellType {
    val empty: CellType = CellType("empty", new Color(200, 200, 200))
    val pheromone_blue: CellType = CellType("pheromone_blue", new Color(30, 30, 200))
    val pheromone_red: CellType = CellType("pheromone_red", new Color(200, 30, 30))
    val obstacle: CellType = CellType("obstacle", new Color(140, 100, 50))
    val colony: CellType = CellType("colony", new Color(200, 100, 30))
    val food: CellType = CellType("food", new Color(70, 200, 0))
}