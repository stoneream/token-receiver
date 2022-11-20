val scala213 = "2.13.10"

ThisBuild / scalaVersion := scala213
ThisBuild / scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(scalaVersion.value)
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
ThisBuild / scalafmtOnCompile := true
ThisBuild / Compile / scalacOptions ++= List(
  "-Ywarn-unused",
  "-Yrangepos"
)
ThisBuild / organization := "com.github.stoneream"
ThisBuild / version := "0.1.0-SNAPSHOT"

lazy val root = (project in file(".")).settings(
  name := "scala-template",
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.2.14"
  )
)

inThisBuild(
  List(
    organization := "com.github.stoneream",
    homepage := Some(url("https://PROJECT-URL-HERE")),
    licenses := List("LICENSE NAME HERE" -> url("https://LICENSE-URL-HERE")),
    developers := List(
      Developer(
        "stoneream",
        "Ishikawa Ryuto",
        "ishikawa-r@protonmail.com",
        url("https://github.com/stoneream")
      )
    )
  )
)
