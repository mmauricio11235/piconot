import sbt._

// from http://alvinalexander.com/scala/using-github-projects-scala-library-dependencies-sbt-sbteclipse
object MyBuild extends Build {
  lazy val root = Project("root", file(".")) dependsOn(langTest)
  lazy val langTest = RootProject(uri("git://github.com/hmc-cs111-fall2014/picolib"))
}