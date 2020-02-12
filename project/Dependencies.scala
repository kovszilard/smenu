import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.1.8"
  lazy val cats = "org.typelevel" %% "cats-core" % "2.1.0"
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % "2.0.0"
  lazy val jline = "org.jline" % "jline" % "3.13.3"
}
