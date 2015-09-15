package me.archdev.restapi.models.db

import scala.concurrent.Future
import me.archdev.restapi.models.CharacterEntity
import slick.driver.JdbcProfile
import scalaz.Tag
import me.archdev.restapi.utils.DatabaseConfig

trait CharacterTable extends DatabaseConfig {

  import driver.api._

  class CharactersTable(tag: Tag) extends Table[CharacterEntity](tag, "Character") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME")

    def * = (id, name) <> (CharacterEntity.tupled, CharacterEntity.unapply _)
  }

  protected val characters = TableQuery[CharactersTable]
}