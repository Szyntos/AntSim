package proc

import proc.Utils.Vector
import org.junit.Test
import org.junit.Assert._


class TestVector {
  var delta : Float = 1e-5f

  @Test
  def testConstructor(): Unit = {
    val v1: Vector = new Vector(5, 5)
    val v2: Vector = new Vector(1.11f, 3.12f)
    val v3: Vector = new Vector(101.11f, 5.1111f)

    assertEquals(v1.x, 5, delta)
    assertEquals(v1.y, 5, delta)
    assertEquals(v2.x, 1.11f, delta)
    assertEquals(v2.y, 3.12f, delta)
    assertNotEquals(v3.x, 5)
    assertNotEquals(v3.y, 5)
  }

  @Test
  def testChangeLength(): Unit = {
    val v1: Vector = new Vector(5, 5)
    val v2: Vector = new Vector(1.11f, 3.12f)
    val v3: Vector = new Vector(101.11f, 5.1111f)

    v1.changeLength()
    v2.changeLength(3.14f)
    v3.changeLength(10.1111f)

    assertEquals(math.sqrt(v1.x * v1.x + v1.y * v1.y).toFloat, 1, delta)
    assertEquals(math.sqrt(v2.x * v2.x + v2.y * v2.y).toFloat, 3.14f, delta)
    assertEquals(math.sqrt(v3.x * v3.x + v3.y * v3.y).toFloat, 10.1111f, delta)
  }

  @Test
  def testCopy(): Unit = {

    val v1: Vector = new Vector(5, 3)
    val v2: Vector = new Vector(132.11f, 311.12f)
    val v3: Vector = new Vector(101.11f, 125.1111f)

    val v1c: Vector = v1.copy()
    val v2c: Vector = v2.copy()
    val v3c: Vector = v3.copy()

    assertEquals(v1.x, v1c.x, delta)
    assertEquals(v1.y, v1c.y, delta)
    assertEquals(v2.x, v2c.x, delta)
    assertEquals(v2.y, v2c.y, delta)
    assertEquals(v3.x, v3c.x, delta)
    assertEquals(v3.y, v3c.y, delta)
  }

  @Test
  def testRotation: Unit = {
    var deltaRotate : Float = 1e-3f

    var v1: Vector = new Vector(1, 0)
    v1.rotate(0f)

    assertEquals(v1.angle, 0, deltaRotate)
    assertEquals(v1.x, 1, deltaRotate)
    assertEquals(v1.y, 0, deltaRotate)

    v1.rotate(2 * 3.1415f)
    assertEquals(v1.angle, 0, deltaRotate)
    assertEquals(v1.x, 1, deltaRotate)
    assertEquals(v1.y, 0, deltaRotate)

    v1.rotate( 3.1415f)
    assertEquals(v1.angle, 3.1415f, deltaRotate)
    assertEquals(v1.x, -1, deltaRotate)
    assertEquals(v1.y, 0, deltaRotate)

    v1.rotate(3.1415f)
    assertEquals(v1.angle, 0, deltaRotate)
    assertEquals(v1.x, 1, deltaRotate)
    assertEquals(v1.y, 0, deltaRotate)

    v1.rotate(-3.1415f)
    assertEquals(v1.angle, -3.1415f, deltaRotate)
    assertEquals(v1.x, -1, deltaRotate)
    assertEquals(v1.y, 0, deltaRotate)

    v1.rotate(-3.1415f)
    assertEquals(v1.angle, 0, deltaRotate)
    assertEquals(v1.x, 1, deltaRotate)
    assertEquals(v1.y, 0, deltaRotate)

    v1 = new Vector(0, 1)
    v1.rotate((3.1415f / 2))

    assertEquals(v1.x, -1, deltaRotate)
    assertEquals(v1.y, 0, deltaRotate)
  }

}