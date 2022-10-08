organization := "com.david"
version := "0.1-SNAPSHOT"
name := "quill-postgres-example"
val sparkVersion = "2.4.1"
scalaVersion := "2.12.8"

val commonDependencies: Seq[ModuleID] = Seq(
  "io.getquill" %% "quill-jasync" % "4.6.0",
  "io.getquill" %% "quill-jasync-postgres" % "4.6.0",
  "io.getquill" %% "quill-jdbc-zio" % "4.6.0",
  "org.slf4j" % "slf4j-log4j12" % "1.7.10",
  "org.postgresql" % "postgresql" % "42.2.7"
)

val log4j : Seq[ModuleID] = Seq("log4j" % "log4j" % "1.2.17")

val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

val root = (project in file(".")).
  settings(
    libraryDependencies ++= commonDependencies,
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-language:_"
    )
  )

