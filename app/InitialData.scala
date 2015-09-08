
import dao.CharacterDAO
import models.Character
import com.google.inject.Inject
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import dao.SentenceDAO
import models.Sentence

class InitialData @Inject() (characterDAO: CharacterDAO, sentenceDAO: SentenceDAO) {

  characterDAO.count.onSuccess { case s => if (s == 0) loadInitialData() }

  def loadInitialData() = {
    Seq(
      Character(1, "Perceval"),
      Character(2, "Arthur")).foreach(characterDAO.insert)

    Seq(
      Sentence(1, "Sentence n°1", 1),
      Sentence(2, "Sentence n°2", 2)).foreach(sentenceDAO.insert)

    Logger info "Initial data loaded."
  }
}