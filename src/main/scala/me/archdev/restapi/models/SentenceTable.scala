package me.archdev.restapi.models

import scala.concurrent.Future
import scala.concurrent.Future
import me.archdev.restapi.utils.DatabaseConfig
import slick.lifted.ProvenShape.proveShapeOf
import me.archdev.restapi.models.CharacterTable._

object SentenceTable extends DatabaseConfig {
  import driver.api._

  private class SentencesTable(tag: Tag) extends Table[Sentence](tag, "Sentence") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def text = column[String]("TEXT")
    def character_code = column[String]("CHARACTER_CODE")
    def * = (id, text, character_code) <> (Sentence.tupled, Sentence.unapply _)

    def author = foreignKey("FK_CHARACTER_CODE", character_code, characters)(_.code)
  }

  private val sentences = TableQuery[SentencesTable]

  def all(): Future[Seq[Sentence]] = db.run(sentences.result)

  //  def insert(Sentence: Sentence): Future[Unit] = db.run(repository += Sentence).map { _ => () }

}