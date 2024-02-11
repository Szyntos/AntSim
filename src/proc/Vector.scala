package proc

class Vector(var x: Float, var y: Float){
    var angle: Float = 0
    var desiredMagn = 1
    def rotate(alpha: Float): Unit = {
        angle += alpha
        angle = angle%(2*3.1415f)
        x = (math.cos(alpha) * x - math.sin(alpha) * y).toFloat
        y = (math.sin(alpha) * x + math.cos(alpha) * y).toFloat
        val magn = math.sqrt(x * x + y * y).toFloat
        x = x / magn * desiredMagn
        y = y / magn * desiredMagn
    }

    def copy(): Vector ={
        val v = new Vector(x, y)
        v.angle = angle
        v
    }

}
