package me.archdev.restapi.models

import me.archdev.restapi.utils.DatabaseConfig
import slick.lifted.ProvenShape.proveShapeOf

trait CharacterTable extends DatabaseConfig {

  import driver.api._

  class CharactersTable(tag: Tag) extends Table[Character](tag, "Character") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME")

    def * = (id, name) <> (Character.tupled, Character.unapply _)
  }

  protected val characters = TableQuery[CharactersTable]
}