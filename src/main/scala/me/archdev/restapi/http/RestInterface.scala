package me.archdev.restapi.http

import me.archdev.restapi.http.resources.CharactersResource
import spray.routing._

import scala.concurrent.ExecutionContext
import scala.language.postfixOps

class RestInterface(implicit val executionContext: ExecutionContext) extends HttpServiceActor with Resources {

  def receive = runRoute(routes)

  val routes: Route = charactersRoutes

}

trait Resources extends CharactersResource