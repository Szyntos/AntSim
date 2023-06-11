package proc

class Ant(val m: AntSim, var x: Float, var y: Float) {
    var orientation: Float = 0

    def drawAnt(): Unit = {
        m.stroke(0)
        m.fill(0)
        m.rect(x, y, 10, 20)
        m.fill(Config.default_color.r, Config.default_color.g, Config.default_color.b)
        m.noStroke()
    }
}
