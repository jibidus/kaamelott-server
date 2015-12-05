package me.archdev.restapi.http.routes

import me.archdev.restapi.services.CharactersService
import org.json4s.JsonDSL._
import spray.routing._

trait CharactersServiceRoute extends CharactersService with BaseServiceRoute {

  val charactersRoutes = pathPrefix("characters") {
    pathEndOrSingleSlash {
      get {
        complete(getCharacters())
      }
    }
  }

}