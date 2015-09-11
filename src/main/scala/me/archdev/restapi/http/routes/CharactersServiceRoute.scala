package me.archdev.restapi.http.routes

import akka.http.scaladsl.server.Directives._
import me.archdev.restapi.services.CharactersService
import akka.http.scaladsl.model.StatusCodes
import me.archdev.restapi.models.Character
import spray.json._

trait CharactersServiceRoute extends CharactersService with BaseServiceRoute {

  import StatusCodes._

  implicit val characterFormat = jsonFormat2(Character)

  val charactersRoute = pathPrefix("characters") {
    pathEndOrSingleSlash {
      get {
        complete(getCharacters().map(_.toJson))
      }
    }
  }

}