package proc

import proc.Utils.Vector

object Direction extends Enumeration {
    type Direction = Value
    val U, D, R, L, UR, UL, DR, DL, C = Value

    def toVector(direction: Direction): Vector ={
        direction match {
            case U => new Vector(0, -1)
            case D => new Vector(0, 1)
            case R => new Vector(1, 0)
            case L => new Vector(-1, 0)
            case UR => new Vector(1, -1)
            case UL => new Vector(-1, -1)
            case DR => new Vector(1, 1)
            case DL => new Vector(-1, 1)
            case C => new Vector(0, 0)
            case _ => new Vector(0, 0)
        }
    }
}
