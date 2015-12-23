package jibidus.kaamelott.server.services

import jibidus.kaamelott.server.models.{Character, CharacterTable}
import jibidus.kaamelott.server.models.CharacterTable._
import jibidus.kaamelott.server.support.DatabaseTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, FunSpec}

// TODO Is this test necessary (CharactersResourceSpec do the job) ?
class CharactersServiceTests extends FunSpec with CharactersService with Matchers with DatabaseTest with ScalaFutures {

  import driver.api._

  describe("#getCharacters") {


    it("should get all characters") {
      // TODO To implement
    }

    it("should return characters well fed") {
      // TODO Move into before block
      db.run(characters += Character("code", "name"))
      getCharacters().futureValue should equal(Seq(Character("code", "name")))
    }

  }
}
