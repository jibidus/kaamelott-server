package jibidus.kaamelott.server.services

import jibidus.kaamelott.server.models.{Character, CharacterTable}
import jibidus.kaamelott.server.models.CharacterTable._
import jibidus.kaamelott.server.support.DatabaseTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, FunSpec}

class CharactersServiceTests extends FunSpec with Matchers with DatabaseTest with CharactersService with ScalaFutures {

  import driver.api._

  describe("#getCharacters") {

    // TODO Ajouter before

    it("should return fed characters") {
      db.run(characters += Character("code", "name"))
      getCharacters().futureValue should equal(Seq(Character("code", "name")))
    }

    it("should get all characters") {
      // TODO
    }

  }
}
