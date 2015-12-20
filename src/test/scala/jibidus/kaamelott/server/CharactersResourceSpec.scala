package jibidus.kaamelott.server

import jibidus.kaamelott.server.http.resources.CharactersResource
import jibidus.kaamelott.server.models.Character
import jibidus.kaamelott.server.models.CharacterTable.characters
import jibidus.kaamelott.server.utils.Migration
import org.json4s.JsonAST._
import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}
import spray.testkit.ScalatestRouteTest

import scala.concurrent.Await
import scala.concurrent.duration._

class CharactersResourceSpec extends FunSpec with BeforeAndAfter with Matchers with ScalatestRouteTest with CharactersResource with Migration {

  import driver.api._

  implicit val executionContext = system.dispatcher

  def actorRefFactory = system

  before {
    reloadSchema()
  }

  describe("Characters routes") {

    it("should return all characters") {

      val testCharacters = Seq(
        Character("arthur", "Arthur"),
        Character("perceval", "Perceval"),
        Character("karadoc", "Karadoc"))

      Await.ready(db.run(characters ++= testCharacters), 10.seconds)

      Get("/characters") ~> charactersRoutes ~> check {
        responseAs[JArray].values.size should be(3)
      }
    }
  }
}