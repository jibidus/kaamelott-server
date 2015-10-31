package me.archdev

import akka.http.scaladsl.model.{ HttpEntity, MediaTypes }
import me.archdev.restapi.http.routes.UsersServiceRoute
import me.archdev.restapi.models.UserEntity
import org.scalatest.concurrent.ScalaFutures
import spray.json._
import akka.http.scaladsl.model.StatusCodes._
import org.scalatest.concurrent.ScalaFutures
import spray.json.JsArray
import support.DatabaseTest
import org.scalatest.WordSpec
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.Matchers
import me.archdev.restapi.http.HttpService
import scala.concurrent.Await
import me.archdev.restapi.models.CharacterTable._
import me.archdev.restapi.models.CharacterTable
import me.archdev.restapi.models.Character
import scala.concurrent.duration._

class CharactersServiceTest extends WordSpec with Matchers with HttpService with ScalatestRouteTest with DatabaseTest with ScalaFutures {

  import driver.api._

  "Caracters service" should {
    "retrieve characters list" in {

      val testCharacters = Seq(
        Character("arthur", "Arthur"),
        Character("perceval", "Perceval"),
        Character("karadoc", "Karadoc"))

      Await.ready(db.run(characters ++= testCharacters), 10.seconds)

      Get("/characters") ~> charactersRoute ~> check {
        responseAs[JsArray] should be(testCharacters.toJson)
      }
    }
  }
}