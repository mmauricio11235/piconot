name := "piconot" // you can change this!

version := "1.0"

scalaVersion := "2.11.2"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource + EclipseCreateSrc.Unmanaged

unmanagedClasspath in (Compile, runMain) += baseDirectory.value / "resources"

//unmanagedClasspath in runMain += baseDirectory.value / "main" / "resources"
