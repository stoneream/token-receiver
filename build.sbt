ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.github.stoneream"

lazy val root = (project in file("."))
  .settings(
    name := "scala-template",
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.8"
    )
  )

scalacOptions ++= List(
  "-Ywarn-unused",
  "-Yrangepos"
)

scalafmtOnCompile := true
