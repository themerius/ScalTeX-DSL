name := "DSL Ideas for ScalTeX"

version := "0.1"

scalaVersion := "2.10.0-RC3"

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases"  at "http://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10.0-RC3" % "1.8-B1",
  "play" %% "play" % "2.1-SNAPSHOT"
)
