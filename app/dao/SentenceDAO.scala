package dao

import scala.concurrent.Future

import javax.inject.Inject
import models.Sentence
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import javax.inject._

@Singleton
class SentenceDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider, protected val characterDAO: CharacterDAO) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val repository = TableQuery[SentencesTable]

  def all(): Future[Seq[Sentence]] = db.run(repository.result)

  def insert(Sentence: Sentence): Future[Unit] = db.run(repository += Sentence).map { _ => () }

  private class SentencesTable(tag: Tag) extends Table[Sentence](tag, "Sentence") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def text = column[String]("TEXT")
    def author_id = column[Long]("CHARACTER_ID")
    def * = (id, text, author_id) <> (Sentence.tupled, Sentence.unapply _)

    def author = foreignKey("SUP_FK", author_id, characterDAO.repository)(_.id)
  }
}