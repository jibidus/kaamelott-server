package jibidus.kaamelott.server.http

import jibidus.kaamelott.server.http.resources.CharactersResource
import spray.routing._

import scala.concurrent.ExecutionContext
import scala.language.postfixOps

class RestInterface(implicit val executionContext: ExecutionContext) extends HttpServiceActor with CharactersResource {

  def receive = runRoute(routes)

  val routes: Route = charactersRoutes

}