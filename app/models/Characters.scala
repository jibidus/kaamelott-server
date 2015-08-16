package models

import play.api.db.slick.Config.driver.simple._

case class Character(id: Long, name: String)

class Characters(tag: Tag) extends Table[Character](tag, "Character") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")
  def * = (id, name) <> (Character.tupled, Character.unapply _)
}

object Characters {
  val query = TableQuery[Characters]
}
