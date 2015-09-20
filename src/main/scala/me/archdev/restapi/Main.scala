package me.archdev.restapi

import akka.actor.ActorSystem
import akka.event.{ Logging, LoggingAdapter }
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import me.archdev.restapi.http.HttpService
import me.archdev.restapi.utils.{ Migration, Config, LoadInitialData }

import scala.concurrent.ExecutionContext

object Main extends App with Config with HttpService with Migration with LoadInitialData {
  private implicit val system = ActorSystem()

  override protected implicit val executor: ExecutionContext = system.dispatcher
  override protected val log: LoggingAdapter = Logging(system, getClass)
  override protected implicit val materializer: ActorMaterializer = ActorMaterializer()

  migrate()
  loadInitialData()

  Http().bindAndHandle(routes, httpInterface, httpPort)
}
