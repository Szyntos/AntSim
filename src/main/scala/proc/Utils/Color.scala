package proc.Utils

class Color(var r: Int, var g: Int, var b: Int){
  def copy(): Color ={
    new Color(r, g, b)
  }
}
