package jibidus.kaamelott.server.http.resources

import jibidus.kaamelott.server.services.CharactersService

trait CharactersResource extends CharactersService with BaseResource {

  val charactersRoutes = pathPrefix("characters") {
    pathEndOrSingleSlash {
      get {
        complete(getCharacters())
      }
    }
  }

}