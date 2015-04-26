package controllers

import play.api.libs.iteratee.Enumerator
import play.api.libs.json.{ JsObject, Json }
import play.api.mvc.{ BodyParsers, Action, Controller }
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import anorm._
import play.api.db.DB
import play.api.Play.current
import anorm.{ SQL, SqlParser }
import play.api.Logger

object CharacterController extends Controller {

  def init = Action.async { implicit request =>
    scala.concurrent.Future {

      val list = List("Angharad", "Arthur")
      DB.withConnection { implicit connection =>
        val insertQuery = SQL("INSERT INTO Character (id, name) values ({id}, 'unknown name')")
        val batchInsert = (insertQuery.asBatch /: list)(
          (sql, elem) => sql.addBatchParams(elem))
        batchInsert.execute()
      }

    }.map(i => Ok("db was initialized with 2 characters"))
  }

  def index = Action { request =>
    DB.withConnection { implicit connection =>
      val first_row = SQL("Select count(*) as c from Character").apply().head
      val character_count = first_row[Long]("c")
      Logger.info(s"$character_count  character(s) found")
      Ok(views.html.characters(character_count))
    }
  }

}