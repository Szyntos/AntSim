package proc

import proc.Utils.{Color, Functions}

class Ant(val m: AntSim, val g: Grid, var x: Float, var y: Float) extends Displayable {
    var vector: Utils.Vector = new Utils.Vector(0, 1)
    vector.desiredMagn = 1
    var xOff: Float = scala.util.Random.nextInt(10) + scala.util.Random.nextFloat()%12
    var yOff: Float = scala.util.Random.nextInt(10)
    color = new Color(0, 0, 0)
    val width: Int = 4
    val height: Int = 6
    var hasFood: Boolean = false
    var X: Int = Functions.boundBy(0, g.width - 1, x.toInt).toInt
    var Y: Int = Functions.boundBy(0, g.height - 1, y.toInt).toInt
    var toSample: CellType = CellType.pheromone_red
    var leaving: CellType = CellType.pheromone_blue

    override def changeColor(): Unit ={
        if (leaving == CellType.pheromone_blue){
            color = new Color(0, 40, 0)
        } else {
            color = new Color(220, 220, 100)
        }
    }

    def indices(): Unit ={
        X = Functions.boundBy(0, g.width - 1, x.toInt).toInt
        Y = Functions.boundBy(0, g.height - 1, y.toInt).toInt
    }

    def obstacleInFront(): Boolean = {
        var newX = Functions.boundBy(0, g.width-1, (x + vector.x).toInt).toInt
        var newY = Functions.boundBy(0, g.height-1, (y + vector.y).toInt).toInt
        if (g.cells(newX)(Y).cell_type == CellType.obstacle ||
            g.cells(X)(newY).cell_type == CellType.obstacle ||
            g.cells(newX)(newY).cell_type == CellType.obstacle){
            return true
        }
        newX = Functions.boundBy(0, g.width - 1, (x + 2*vector.x).toInt).toInt
        newY = Functions.boundBy(0, g.height - 1, (y + 2*vector.y).toInt).toInt
        if (g.cells(newX)(newY).cell_type == CellType.obstacle) {
            return true
        }
        false
    }

    def takeSamples(cellType: CellType, samples: Int=20): Direction.Direction = {
//        val samples: Int = 20
        val aOff: Float = 3.14f/20
        var newX: Int = 0
        var newY: Int = 0
        var leftCount: Float = 0
        var rightCount: Float = 0
        var forwardCount: Float = 0
        val leftVector = vector.copy()
        val rightVector = vector.copy()
        val forwardVector = vector.copy()
        var flag: Boolean = false
        for (i <- -4 to -2){
            flag = false
            leftVector.rotate(aOff * i)
            for (j <- 1 to samples){
                newX = Functions.onTorus(0, g.width, (x + j * leftVector.x).toInt).toInt
                newY = Functions.onTorus(0, g.height, (y + j * leftVector.y).toInt).toInt
                if (g.cells(newX)(newY).cell_type == cellType && !flag){
                    leftCount += g.cells(newX)(newY).life_time
//                    leftCount += 1
                } else if (g.cells(newX)(newY).cell_type == CellType.obstacle  && cellType != CellType.obstacle){
                    flag = true
                }


            }
        }
        for (i <- -1 to 1) {
            flag = false
            forwardVector.rotate(aOff * i)
            for (j <- 1 to samples) {
                newX = Functions.onTorus(0, g.width, (x + j * forwardVector.x).toInt).toInt
                newY = Functions.onTorus(0, g.height, (y + j * forwardVector.y).toInt).toInt
                if (g.cells(newX)(newY).cell_type == cellType && !flag) {
                    forwardCount += g.cells(newX)(newY).life_time
                    //                    leftCount += 1
                } else if (g.cells(newX)(newY).cell_type == CellType.obstacle && cellType != CellType.obstacle) {
                    flag = true
                }
//                g.cells(newX)(newY).changeType(CellType.colony)
            }
            forwardVector.rotate(aOff * -i)

        }
        for (i <- 2 to 4) {
            flag = false
            rightVector.rotate(aOff * i)
            for (j <- 1 to samples) {
                newX = Functions.onTorus(0, g.width, (x + j * rightVector.x).toInt).toInt
                newY = Functions.onTorus(0, g.height, (y + j * rightVector.y).toInt).toInt
                if (g.cells(newX)(newY).cell_type == cellType && !flag) {
                    rightCount += g.cells(newX)(newY).life_time
                    //                    leftCount += 1
                } else if (g.cells(newX)(newY).cell_type == CellType.obstacle && cellType != CellType.obstacle) {
                    flag = true
                }
            }
        }
        if (forwardCount > leftCount && forwardCount > rightCount){
            Direction.U
        }else if (forwardCount < leftCount && forwardCount < rightCount){
            if (leftCount < rightCount){
                Direction.R
            }else{
                Direction.L
            }
        }else if (leftCount < rightCount){
            Direction.R
        }else if (rightCount < leftCount){
            Direction.L
        }else if (rightCount != 0 && forwardCount != 0 && leftCount != 0){
            Direction.U
        }else {
            Direction.D
        }
    }

    def onType(cellType: CellType): Boolean = {

        if (g.cells(X)(Y).cell_type == cellType) {
            return true
        }
        false
    }

    def takeFood(): Unit = {
        g.cells(X)(Y).changeType(CellType.empty)
    }

    def move(): Unit = {
        xOff = xOff + 0.02f
        val a: Float = (m.noise(xOff, yOff + 1) - 0.5f) / 10f
        if (obstacleInFront()){
            vector.rotate(scala.util.Random.nextFloat()%(3.14f/2)+1)


        } else {
            takeSamples(CellType.obstacle, 2) match {
                case Direction.R => vector.rotate(-1f + a / 3)
                case Direction.L => vector.rotate(1f + a / 3)
                case Direction.U => vector.rotate(3.14f + scala.util.Random.nextFloat() % 0.2f)
                case _ => vector.rotate(a)
            }

//            println(takeSamples(toSample))
            if (toSample == CellType.pheromone_red) {
                takeSamples(CellType.food) match {
                    case Direction.R => vector.rotate(0.4f + a / 3)
                    case Direction.L => vector.rotate(-0.4f + a / 3)
                    case _ => ()
                }
            } else {
                takeSamples(CellType.colony) match {
                    case Direction.R => vector.rotate(0.3f + a / 3)
                    case Direction.L => vector.rotate(-0.3f + a / 3)
                    case _ => ()
                }
            }
            takeSamples(toSample) match {
                case Direction.R => vector.rotate(0.2f + a/3)
                case Direction.L => vector.rotate(-0.2f + a/3)
                case Direction.U => ()
                case _ => vector.rotate(a)
            }
        }
        x += vector.x
        y += vector.y
        if (x <= 0){
            x = g.width-1
        }
        if (x >= g.width) {
            x = 0
        }
        if (y <= 0) {
            y = g.height-1
        }
        if (y >= g.height) {
            y = 0
        }
        if (onType(CellType.obstacle)){
            x -= 2 * vector.x
            y -= 2 * vector.y
        }
        indices()

        if (onType(CellType.food)){
            if (!hasFood){
                takeFood()
                hasFood = true
                vector.rotate(3.14f/3 * 2 )
            }
        } else if (onType(CellType.colony)){
            if (hasFood) {
                hasFood = false
                vector.rotate(3.14f / 3 * 2 )
            }

        }

        if (hasFood){
            leaving = CellType.pheromone_red
            toSample = CellType.pheromone_blue
        } else{
            leaving = CellType.pheromone_blue
            toSample = CellType.pheromone_red
        }
        leavePheromone(leaving)
        changeColor()

    }


    def leavePheromone(pType: CellType): Unit = {
        val newX = Functions.boundBy(0, g.width-1, x.toInt).toInt
        val newY = Functions.boundBy(0, g.width-1, y.toInt).toInt
        if (g.cells(newX)(newY).cell_type == CellType.empty){
            g.cells(newX)(newY).changeType(pType)
        }
    }
    override def draw(): Unit = {
        m.stroke(0)

        m.pushMatrix()

        m.translate(x*(m.ScreenDimension/g.width) + (m.ScreenDimension/g.width)/2,
            y*(m.ScreenDimension/g.height) + (m.ScreenDimension/g.height)/2)
        m.rotate(vector.angle)
        m.fill(color.r, color.g, color.b)
        m.rect(0, 0, width, height)

        m.popMatrix()
        m.noStroke()
    }
}
