name := "piconot" // you can change this!

version := "1.0"

scalaVersion := "2.11.2"

libraryDependencies ++= 
  Seq( "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
       "org.scalafx" % "scalafx_2.11" % "8.0.5-R5" )

unmanagedClasspath in (Compile, runMain) += baseDirectory.value / "resources"
