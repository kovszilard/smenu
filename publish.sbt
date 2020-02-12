ThisBuild / description := "Simple menus for Scala console applications"
ThisBuild / homepage := Some(url("https://github.com/kovszilard/smenu"))
ThisBuild / organizationHomepage := Some(url("https://github.com/kovszilard/"))
ThisBuild / licenses := List("MIT" -> new URL("http://opensource.org/licenses/MIT"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/kovszilard/smenu"),
    "scm:git@github.com:kovszilard/smenu.git"
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
// Not needed since sbt-sonatype is usd to publish artifacts
//ThisBuild / publishTo := {
//  val nexus = "https://oss.sonatype.org/"
//  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
//  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
//}
ThisBuild / publishTo := sonatypePublishToBundle.value
ThisBuild / publishMavenStyle := true

ThisBuild / credentials += Credentials(Path.userHome / ".sbt" / "sonatype_credential")
