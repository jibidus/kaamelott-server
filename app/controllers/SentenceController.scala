package controllers

import models._
import play.api._
import play.api.db.slick._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.Play.current
import play.api.mvc.BodyParsers._
import play.api.libs.json.Json
import play.api.libs.json.Json._
import dao.SentenceDAO
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global

class SentenceController @Inject() (sentenceDAO: SentenceDAO) extends Controller {

  implicit val sentence_format = Json.format[Sentence]

  def index = Action.async { implicit request =>
    sentenceDAO.all().map(r => Ok(toJson(r)))
  }

}