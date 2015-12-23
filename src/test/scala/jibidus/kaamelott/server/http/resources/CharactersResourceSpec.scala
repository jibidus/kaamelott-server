package jibidus.kaamelott.server.http.resources

import jibidus.kaamelott.server.models.Character
import jibidus.kaamelott.server.models.CharacterTable.characters
import jibidus.kaamelott.server.support.DatabaseTest
import org.json4s.JsonAST._
import org.scalatest.{FunSpec, Matchers}
import spray.testkit.ScalatestRouteTest

import scala.concurrent.Await
import scala.concurrent.duration._

class CharactersResourceSpec extends FunSpec with CharactersResource with Matchers with ScalatestRouteTest with DatabaseTest {

  implicit val executionContext = system.dispatcher

  def actorRefFactory = system

  import driver.api._

  describe("Characters routes") {

    it("should return all characters") {
      // TODO Move into before block
      val testCharacters = Seq(
        Character("arthur", "Arthur"),
        Character("perceval", "Perceval"),
        Character("karadoc", "Karadoc"))

      Await.ready(db.run(characters ++= testCharacters), 10.seconds)

      Get("/characters") ~> charactersRoutes ~> check {
        responseAs[JArray].values.size should be(testCharacters.size)
      }
    }

    it("should return characters with corresponding properties") {
      // TODO To implement
    }
  }
}