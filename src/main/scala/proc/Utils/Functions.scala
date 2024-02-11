package proc.Utils

object Functions {
  def boundBy(min: Float, max: Float, value: Float): Float = {
    scala.math.min(scala.math.max(value, min), max)
  }

  def onTorus(min: Float, max: Float, value: Float): Float = {
    if (value <= min) {
      return max - 1
    }
    if (value >= max) {
      return min
    }
    value
  }
}
