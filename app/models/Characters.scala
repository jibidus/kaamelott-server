package models

import play.api.db.slick.Config.driver.simple._

case class Character(id: Long, text: String)

class Characters(tag: Tag) extends Table[Character](tag, "Character") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def text = column[String]("TEXT")
  def * = (id, text) <> (Character.tupled, Character.unapply _)
}
//val characters = TableQuery[Characters]