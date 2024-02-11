ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "ants"
  )

Compile/mainClass := Some("proc.AntSim")
libraryDependencies += "com.github.sbt" % "junit-interface" % "0.13.3" % Test