package jibidus.kaamelott.server.http.resources

import jibidus.kaamelott.server.services.CharactersService
import org.json4s.JsonDSL._
import spray.routing._

trait CharactersResource extends CharactersService with BaseResource {

  val charactersRoutes = pathPrefix("characters") {
    pathEndOrSingleSlash {
      get {
        complete(getCharacters())
      }
    }
  }

}