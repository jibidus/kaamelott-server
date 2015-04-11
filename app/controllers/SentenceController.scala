package controllers

import play.api.libs.iteratee.Enumerator
import play.api.libs.json.{ JsObject, Json }
import play.api.mvc.{ BodyParsers, Action, Controller }
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import anorm._
import play.api.db.DB
import play.api.Play.current
import anorm.{ SQL, SqlParser }

object SentenceController extends Controller {

  def init = Action.async { implicit request =>
    scala.concurrent.Future {

      val list = List("Angharad", "Arthur")
      DB.withConnection { implicit connection =>
        val insertQuery = SQL("INSERT INTO Character (id, name) values ({id}, 'unknown name')")
        val batchInsert = (insertQuery.asBatch /: list)(
          (sql, elem) => sql.addBatchParams(elem))
        batchInsert.execute()
      }

    }.map(i => Ok("db was initialized with sentences"))
  }

  def index = Action { request =>
    DB.withConnection { implicit connection =>
      val firstRow = SQL("Select count(*) as c from Character").apply().head
      val countryCount = firstRow[Long]("c")
      Ok("number of characters: " + countryCount)
    }
  }

}