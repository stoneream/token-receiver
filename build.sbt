// === scala settings ===

inThisBuild(
  List(
    scalaVersion := "2.13.10",
    scalacOptions ++= List(
      "-Ywarn-unused",
      "-Yrangepos",
      "-Ywarn-macros:after"
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

sonatypeCredentialHost := "s01.oss.sonatype.org"
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"

lazy val publishSettings = Seq(
  publish / skip := false,
  Test / publishArtifact := false,
  versionScheme := Some("early-semver")
)

// === project setting ===

lazy val root = (project in file(".")).settings(
  name := "token-receiver",
  publish / skip := true
).aggregate(core, example)

lazy val core = (project in file("core")).settings(
  name := "token-receiver-core",
  libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % "1.4.5",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"
  ),
  publishSettings
)

lazy val sttpVersion = "3.8.8"
lazy val circeVersion = "0.14.3"

lazy val example = (project in file("example")).settings(
  name := "token-receiver-example",
  libraryDependencies ++= Seq(
    "com.softwaremill.sttp.client3" %% "core" % sttpVersion,
    "com.softwaremill.sttp.client3" %% "circe" % sttpVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.circe" %% "circe-generic-extras" % circeVersion,
    "io.circe" %% "circe-literal" % circeVersion,
  ),
  publish / skip := true
).dependsOn(core)
