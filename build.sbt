import com.typesafe.config._
import scalariform.formatter.preferences._

name          := """kaamelott-server"""
organization  := "jibidus"
version       := "0.1-SNAPSHOT"
scalaVersion  := "2.11.7"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= {
  val sprayVersion 	   = "1.3.3"
  val scalazV          = "7.2.0-M1"
  val akkaV      	   = "2.3.14"
  val akkaStreamV      = "1.0"
  val scalaTestV       = "3.0.0-M1"
  val scalaMockV       = "3.2.2"
  val scalazScalaTestV = "0.2.3"
  val slickV     	   = "3.0.3"
  
  Seq(
  
    "io.spray"           %%   "spray-can"     					   % sprayVersion,
    "io.spray"           %%   "spray-routing" 					   % sprayVersion,
    "io.spray"           %%   "spray-testkit" 					   % sprayVersion % "test",
    "org.json4s" 		 %% "json4s-native" 					   % "3.3.0.RC6",
    "org.json4s" 		 %% "json4s-ext" 						   % "3.3.0.RC6",
    "com.typesafe.akka"  %% "akka-slf4j" 						   % akkaV,
    "com.typesafe.slick" %% "slick"                                % slickV,
    "ch.qos.logback" 	 %  "logback-classic" 					   % "1.0.9",
    "com.h2database" 	 %  "h2" 								   % "1.4.188",
    "org.mindrot"        %  "jbcrypt"                              % "0.3m",
    "org.flywaydb"       %  "flyway-core"                          % "3.2.1",
    "org.scalatest"      %% "scalatest"                            % scalaTestV       % "it,test",
    "org.scalamock"      %% "scalamock-scalatest-support"          % scalaMockV       % "it,test",
    "org.scalaz"         %% "scalaz-scalacheck-binding"            % scalazV          % "it,test",
    "org.typelevel"      %% "scalaz-scalatest"                     % scalazScalaTestV % "it,test"
  )
}

lazy val root = project.in(file(".")).configs(IntegrationTest)
Defaults.itSettings
scalariformSettings
Revolver.settings
enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

dockerExposedPorts := Seq(9000)

dockerEntrypoint := Seq("bin/%s" format executableScriptName.value, "-Dconfig.resource=docker.conf")

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)

initialCommands := """|import scalaz._
                      |import Scalaz._
                      |import akka.actor._
                      |import akka.pattern._
                      |import akka.util._
                      |import scala.concurrent._
                      |import scala.concurrent.duration._""".stripMargin

parallelExecution in Test := false

fork in run := true