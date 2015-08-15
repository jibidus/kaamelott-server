package models

import play.api.db.slick.Config.driver.simple._

case class Sentence(id: Long, text: String, author_id: Long)

class Sentences(tag: Tag) extends Table[Sentence](tag, "Sentence") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def text = column[String]("TEXT")
  def author_id = column[Long]("CHARACTER_ID")
  def * = (id, text, author_id) <> (Sentence.tupled, Sentence.unapply _)

  def author = foreignKey("SUP_FK", author_id, Characters.query)(_.id)
}

object Sentences {
  val query = TableQuery[Sentences]
}