package jibidus.kaamelott.server.utils

import org.scalatest.FunSpec
import jibidus.kaamelott.server.models.CharacterTable._
import jibidus.kaamelott.server.models.CharacterTable
import jibidus.kaamelott.server.models.Character
import scala.concurrent.Await
import scala.concurrent.duration._
import org.scalatest.BeforeAndAfter
import jibidus.kaamelott.server.support.DatabaseTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.Matchers

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