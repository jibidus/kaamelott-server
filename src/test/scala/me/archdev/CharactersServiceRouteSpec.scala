package me.archdev

import org.scalatest.FunSpec
import spray.testkit.ScalatestRouteTest
import spray.http.HttpHeaders.Accept
import spray.http.HttpRequest
import me.archdev.restapi.http.routes.CharactersServiceRoute
import support.DatabaseTest
import scala.concurrent.duration._
import me.archdev.restapi.models.Character
import me.archdev.restapi.models.CharacterTable.characters
import me.archdev.restapi.models.CharacterTable
import scala.concurrent.Await
import org.scalatest.Matchers
import me.archdev.restapi.utils.Migration
import scala.concurrent.ExecutionContext
import org.json4s.JsonAST._

class CharactersServiceRouteSpec extends FunSpec with Matchers with ScalatestRouteTest with CharactersServiceRoute with Migration {
  import driver.api._
  implicit val executionContext = system.dispatcher
  def actorRefFactory = system

  describe("Characters routes") {

    it("should return all characters") {

      val testCharacters = Seq(
        Character("arthur", "Arthur"),
        Character("perceval", "Perceval"),
        Character("karadoc", "Karadoc"))

      Await.ready(db.run(characters ++= testCharacters), 10.seconds)

      Get("/characters") ~> charactersRoutes ~> check {
        responseAs[JArray].values.size should be(4)
      }
    }
  }
}