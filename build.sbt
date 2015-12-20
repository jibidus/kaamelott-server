import com.typesafe.config._
import scalariform.formatter.preferences._

name := "kaamelott-server"
organization := "jibidus"
version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= {
  val sprayVersion = "1.3.3"
  val scalazV = "7.2.0-M1"
  val akkaV = "2.3.14"
  val akkaStreamV = "1.0"
  val scalaTestV = "3.0.0-M1"
  val scalaMockV = "3.2.2"
  val scalazScalaTestV = "0.2.3"
  val slickV = "3.0.3"
  val json4sV = "3.3.0.RC6"

  Seq(

    "io.spray" %% "spray-can" % sprayVersion,
    "io.spray" %% "spray-routing" % sprayVersion,
    "org.json4s" %% "json4s-native" % json4sV,
    "org.json4s" %% "json4s-ext" % json4sV,

    "com.typesafe.akka" %% "akka-slf4j" % akkaV,
    "ch.qos.logback" % "logback-classic" % "1.0.9",

    "com.typesafe.slick" %% "slick" % slickV,
    "com.h2database" % "h2" % "1.4.188",

    "org.flywaydb" % "flyway-core" % "3.2.1",

    "io.spray" %% "spray-testkit" % sprayVersion % "test",
    "org.scalatest" %% "scalatest" % scalaTestV % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockV % "test",
    "org.scalaz" %% "scalaz-scalacheck-binding" % scalazV % "test",
    "org.typelevel" %% "scalaz-scalatest" % scalazScalaTestV % "test"
  )
}

parallelExecution := false

lazy val root = project.in(file(".")).configs(IntegrationTest)
Defaults.itSettings
scalariformSettings
Revolver.settings
enablePlugins(JavaAppPackaging)

