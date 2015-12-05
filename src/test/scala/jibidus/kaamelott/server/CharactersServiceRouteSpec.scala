package jibidus.kaamelott.server

import org.scalatest.FunSpec
import spray.testkit.ScalatestRouteTest
import spray.http.HttpHeaders.Accept
import spray.http.HttpRequest
import jibidus.kaamelott.server.http.resources.CharactersResource
import support.DatabaseTest
import scala.concurrent.duration._
import jibidus.kaamelott.server.models.Character
import jibidus.kaamelott.server.models.CharacterTable.characters
import jibidus.kaamelott.server.models.CharacterTable
import scala.concurrent.Await
import org.scalatest.Matchers
import jibidus.kaamelott.server.utils.Migration
import scala.concurrent.ExecutionContext
import org.json4s.JsonAST._

class CharactersServiceRouteSpec extends FunSpec with Matchers with ScalatestRouteTest with CharactersResource with Migration {
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
        responseAs[JArray].values.size should be(0)
      }
    }
  }
}