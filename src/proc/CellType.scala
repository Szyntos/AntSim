package proc

object CellType extends Enumeration {
    type CellType = Value
    val empty, pheromone_blue, pheromone_red, obstacle, colony, food = Value
}

