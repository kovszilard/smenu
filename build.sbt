import Dependencies._

lazy val scala212 = "2.12.10"
lazy val scala213 = "2.13.13"
lazy val supportedScalaVersions = List(scala213, scala212)

ThisBuild / scalaVersion     := scala213
ThisBuild / organization     := "com.github.kovszilard"
ThisBuild / organizationName := "kovszilard"

import ReleaseTransformations._
ThisBuild / releaseCrossBuild := true // true if you cross-build the project for multiple Scala versions
ThisBuild / releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  // For non cross-build projects, use releaseStepCommand("publishSigned")
  releaseStepCommandAndRemaining("+publishSigned"),
  releaseStepCommand("sonatypeBundleRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)

useGpgAgent := true
useGpgPinentry := true

lazy val root = (project in file("."))
  .aggregate(smenu, example)
  .settings(
    crossScalaVersions := Nil,
    skip in publish := true
  )

lazy val smenu = (project in file("smenu"))
  .settings(
    name := "smenu",
    crossScalaVersions := supportedScalaVersions,
    libraryDependencies ++= Seq(scalaTest % Test, cats, catsEffect, jline),
    scalacOptions ++= Seq("-Xfatal-warnings", "-Xlint", "-feature", "-language:higherKinds"),
  )

lazy val example = (project in file("example"))
  .settings(
    name := "example",
    crossScalaVersions := supportedScalaVersions,
    skip in publish := true,
    libraryDependencies ++= Seq(cats, catsEffect)
  ).dependsOn(smenu)
