package proc

import processing.core.PApplet


object ProcessingTest extends PApplet{
  def main(args: String*): Unit = {
    PApplet.main("proc.ProcessingTest")
  }

  override def settings(): Unit = {
    size(200, 200)
  }

  override def draw(): Unit = {
    background(0)
    ellipse(mouseX, mouseY, 20, 20)
  }
}
