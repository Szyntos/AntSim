package proc

import proc.Utils.Color

trait Displayable {
  var color: Color = Config.default_color
  def changeColor(): Unit
  def draw(): Unit
}