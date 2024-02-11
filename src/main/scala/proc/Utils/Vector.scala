package proc.Utils

class Vector(var x: Float, var y: Float){
    var angle: Float = 0
    var desiredMagn = 1


    def rotate(alpha: Float): Unit = {
        angle += alpha
        angle = angle%(2*3.1415f)
        val newX = (math.cos(alpha) * x - math.sin(alpha) * y).toFloat
        val newY = (math.sin(alpha) * x + math.cos(alpha) * y).toFloat
        x = newX
        y = newY
        changeLength(desiredMagn)



    }

    def copy(): Vector ={
        val v = new Vector(x, y)
        v.angle = angle
        v
    }

    def changeLength(magn : Float = 1): Unit = {
        val currMagn = math.sqrt(x * x + y * y).toFloat
        x = x / currMagn
        y = y / currMagn
    }
}
