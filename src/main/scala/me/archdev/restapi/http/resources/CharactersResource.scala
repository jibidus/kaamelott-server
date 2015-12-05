package me.archdev.restapi.http.resources

import me.archdev.restapi.services.CharactersService
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