package me.archdev.restapi.models

import scala.concurrent.Future
import scala.concurrent.Future
import me.archdev.restapi.utils.DatabaseConfig
import slick.lifted.ProvenShape.proveShapeOf

trait SentenceTable extends DatabaseConfig {
  import driver.api._

  private val sentences = TableQuery[SentencesTable]

  def all(): Future[Seq[Sentence]] = db.run(sentences.result)

  //  def insert(Sentence: Sentence): Future[Unit] = db.run(repository += Sentence).map { _ => () }

  private class SentencesTable(tag: Tag) extends Table[Sentence](tag, "Sentence") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def text = column[String]("TEXT")
    def author_id = column[Long]("CHARACTER_ID")
    def * = (id, text, author_id) <> (Sentence.tupled, Sentence.unapply _)

    def author = foreignKey("SUP_FK", author_id, sentences)(_.id)
  }
}