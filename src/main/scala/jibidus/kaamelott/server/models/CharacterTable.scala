package jibidus.kaamelott.server.models

import jibidus.kaamelott.server.utils.DatabaseConfig
import slick.lifted.ProvenShape.proveShapeOf
import scala.concurrent.Future

object CharacterTable extends DatabaseConfig {
  import driver.api._

  class CharactersTable(tag: Tag) extends Table[Character](tag, "Character") {
    def code = column[String]("CODE", O.PrimaryKey)
    def name = column[String]("NAME")

    def * = (code, name) <> (Character.tupled, Character.unapply _)
  }

  val characters = TableQuery[CharactersTable]

  def deleteAll(): Future[Int] = db.run(characters.delete)

  def all(): Future[Seq[Character]] = db.run(characters.result)

  def count(): Future[Int] = db.run(characters.length.result)
}