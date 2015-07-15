package models

import play.api.db.slick.Config.driver.simple._

case class Sentence(id: Long, text: String)

class Sentences(tag: Tag) extends Table[Sentence](tag, "Sentence") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def text = column[String]("TEXT")
  def * = (id, text) <> (Sentence.tupled, Sentence.unapply _)
}