package proc

object Functions {
    def boundBy(min: Float, max: Float, value: Float): Float ={
        scala.math.min(scala.math.max(value, min), max)
    }
}
