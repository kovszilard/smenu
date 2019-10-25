ThisBuild / description := "Simple menus for Scala console applications"
ThisBuild / homepage := Some(url("https://github.com/kovszilard/menu"))
ThisBuild / organizationHomepage := Some(url("https://github.com/kovszilard/"))
ThisBuild / licenses := List("MIT" -> new URL("http://opensource.org/licenses/MIT"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/kovszilard/menu"),
    "scm:git@github.com:kovszilard/menu.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id    = "kovszilard",
    name  = "Szilard Kovacs",
    email = "",
    url   = url("https://github.com/kovszilard/")
  )
)

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true
