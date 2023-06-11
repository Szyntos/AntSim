package proc

import processing.core.PApplet

import scala.math.{Pi, cos, sin}

/** A Processing applet with some enhancements for Scala programmers */
abstract class ScalaProcessingApplet extends PApplet {

    /** Runs the given function within a pair of push/popMatrix calls */
    protected def withPushedMatrix(fn: => Unit): Unit = {
        pushMatrix()
        fn
        popMatrix()
    }
}