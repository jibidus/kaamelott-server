package me.archdev

import akka.http.scaladsl.model.{ HttpEntity, MediaTypes }
import me.archdev.restapi.http.routes.UsersServiceRoute
import me.archdev.restapi.models.UserEntity
import org.scalatest.concurrent.ScalaFutures

import spray.json._
import akka.http.scaladsl.model.StatusCodes._
import org.scalatest.concurrent.ScalaFutures
import spray.json.JsArray

class CharactersServiceTest extends BaseServiceTest with ScalaFutures {
  "Caracters service" should {
    "retrieve characters list" in {
      Get("/characters") ~> charactersRoute ~> check {
        responseAs[JsArray] should be(testCharacters.toJson)
      }
    }
  }
}