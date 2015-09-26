package utils

import org.scalatest.FunSpec
import me.archdev.restapi.utils.LoadInitialData
import me.archdev.restapi.models.CharacterTable

class LoadInitialDataTest extends FunSpec with LoadInitialData {

  describe("LoadInitialData") {
    it("should load characters") {
      loadInitialData
      assert(CharacterTable.characters.size !== 0)
    }
  }

}