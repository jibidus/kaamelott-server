package jibidus.kaamelott.server

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.util.Timeout
import jibidus.kaamelott.server.http.RestInterface
import jibidus.kaamelott.server.utils.{Config, LoadInitialData, Migration}
import spray.can.Http

import scala.concurrent.duration.DurationInt

object Main extends App with Migration with LoadInitialData {
  private implicit val system = ActorSystem()
  private implicit val executionContext = system.dispatcher
  private implicit val timeout = Timeout(10.seconds)

  migrate()
  loadInitialData()

  val service = system.actorOf(Props(new RestInterface))
  IO(Http) ! Http.Bind(service, Config.httpInterface, Config.httpPort)
}
