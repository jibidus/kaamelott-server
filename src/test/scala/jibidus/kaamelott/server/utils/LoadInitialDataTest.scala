package jibidus.kaamelott.server.utils

import jibidus.kaamelott.server.models.{Character, CharacterTable}
import jibidus.kaamelott.server.models.CharacterTable._
import jibidus.kaamelott.server.support.DatabaseTest
import org.scalatest.{FunSpec, Matchers}
import org.scalatest.concurrent.ScalaFutures

class LoadInitialDataTest extends FunSpec with Matchers with DatabaseTest with LoadInitialData with ScalaFutures {

  import driver.api._

  describe("LoadInitialData") {

    it("should load some characters") {
      loadInitialData
      CharacterTable.count.futureValue should not equal 0
    }

    it("should not load characters if at least one character already exists") {
      db.run(characters += Character("code", "one character")).isCompleted
      loadInitialData should equal(None)
      CharacterTable.count.futureValue should equal(1)
    }
  }

}