package me.archdev.restapi

import akka.actor.ActorSystem
import akka.event.{ Logging, LoggingAdapter }
import akka.actor.{ ActorSystem, Props }
import akka.io.IO
import spray.can.Http
import me.archdev.restapi.utils.{ Migration, Config, LoadInitialData }
import scala.concurrent.ExecutionContext
import me.archdev.restapi.http.RestInterface
import akka.util.Timeout
import scala.concurrent.duration._

object Main extends App with Migration with LoadInitialData {
  private implicit val system = ActorSystem()
  private implicit val executionContext = system.dispatcher
  private implicit val timeout = Timeout(10.seconds)

  migrate()
  loadInitialData()

  val service = system.actorOf(Props(new RestInterface))
  IO(Http) ! Http.Bind(service, Config.httpInterface, Config.httpPort)
}
