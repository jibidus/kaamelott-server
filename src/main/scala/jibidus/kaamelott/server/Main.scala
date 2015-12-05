package jibidus.kaamelott.server

import scala.concurrent.duration.DurationInt

import akka.actor.ActorSystem
import akka.actor.Props
import akka.io.IO
import akka.util.Timeout
import jibidus.kaamelott.server.http.RestInterface
import jibidus.kaamelott.server.utils.Config
import jibidus.kaamelott.server.utils.LoadInitialData
import jibidus.kaamelott.server.utils.Migration
import spray.can.Http

object Main extends App with Migration with LoadInitialData {
  private implicit val system = ActorSystem()
  private implicit val executionContext = system.dispatcher
  private implicit val timeout = Timeout(10.seconds)

  migrate()
  loadInitialData()

  val service = system.actorOf(Props(new RestInterface))
  IO(Http) ! Http.Bind(service, Config.httpInterface, Config.httpPort)
}
