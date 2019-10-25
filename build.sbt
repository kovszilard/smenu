import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.github.kovszilard"
ThisBuild / organizationName := "kovszilard"

useGpg := true
useGpgAgent := true
useGpgPinentry := true

lazy val root = (project in file("."))
  .aggregate(smenu, example)
  .settings(
      skip in publish := true
  )

lazy val smenu = (project in file("smenu"))
  .settings(
      name := "smenu",
      crossScalaVersions := Seq("2.13.1", "2.12.10"),
      libraryDependencies ++= Seq(scalaTest % Test, cats, catsEffect, jline),
      scalacOptions ++= Seq("-Xfatal-warnings", "-Xlint", "-feature", "-language:higherKinds")
  )

lazy val example = (project in file("example"))
  .settings(
      name := "example",
      crossScalaVersions := Seq("2.13.1", "2.12.10"),
    libraryDependencies ++= Seq(cats, catsEffect),
      skip in publish := true
  ).dependsOn(smenu)