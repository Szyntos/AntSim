package proc

import scala.math.{Pi, cos, sin, sqrt}
import scala.util.Random
import processing.core.{PApplet, PConstants}
import processing.event.KeyEvent

/** Draws 3D spirals */
class AntSim extends ScalaProcessingApplet {
  val random = new Random()
  val ScreenDimension = 800
  var d: Float = 100

  override def settings(): Unit = size(ScreenDimension, ScreenDimension)

  override def setup(): Unit = {
    frameRate(60)
    background(0)
    colorMode(PConstants.RGB, 1F)
  }
  def dist(x1: Int, y1: Int, x2: Int, y2: Int): Float = {
    sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)).toFloat
  }

  override def mousePressed(): Unit = {
    d = 2 * dist(mouseX, mouseY, width / 2, height / 2)
  }

  override def mouseDragged(): Unit = {
    d = 2 * dist(mouseX, mouseY, width / 2, height / 2)
  }

  override def draw(): Unit = {
    noStroke()
    background(255)
    ellipse(width / 2, height / 2, d, d)
  }
}

object AntSim {
  def main(args: Array[String]): Unit = PApplet.main("proc.AntSim")
}