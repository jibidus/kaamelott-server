package dao

import scala.concurrent.Future

import javax.inject.Inject
import models.Character
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

class CharacterDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val Characters = TableQuery[CharactersTable]

  def repository = Characters

  def all(): Future[Seq[Character]] = db.run(Characters.result)

  def insert(character: Character): Future[Unit] = db.run(Characters += character).map { _ => () }

  class CharactersTable(tag: Tag) extends Table[Character](tag, "Character") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME")
    def * = (id, name) <> (Character.tupled, Character.unapply _)
  }
}