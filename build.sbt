// === scala settings ===

inThisBuild(
  List(
    scalaVersion := "2.13.10",
    scalacOptions ++= List(
      "-Ywarn-unused",
      "-Yrangepos"
    ),
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(scalaVersion.value),
    scalafmtOnCompile := true
  )
)

// === project info ===

inThisBuild(
  List(
    organization := "io.github.stoneream",
    homepage := Some(url("https://github.com/stoneream/token-receiver")),
    licenses := List("Apache License 2.0" -> url("https://raw.githubusercontent.com/stoneream/token-receiver/main/LICENSE")),
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

// === publish settings ===

/*
sonatypeCredentialHost := "s01.oss.sonatype.org"
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"

lazy val publishSettings = Seq(
  publish / skip := false,
  Test / publishArtifact := false,
  versionScheme := Some("early-semver")
)
 */

// === project setting ===

lazy val root = (project in file(".")).settings(
  name := "token-receiver",
  libraryDependencies ++= Seq(
    "com.softwaremill.sttp.client3" %% "core" % "3.8.8",
    "ch.qos.logback" % "logback-classic" % "1.4.5",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    "org.scalatest" %% "scalatest" % "3.2.14" % Test,
  ),
  publish / skip := true
)
