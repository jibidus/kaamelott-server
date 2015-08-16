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
import javax.inject.Inject
import dao.CharacterDAO
import dao.SentenceDAO
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class CharacterController @Inject() (sentenceDAO: SentenceDAO, characterDAO: CharacterDAO) extends Controller {

  implicit val character_format = Json.format[Character]

  def init = Action.async { implicit request =>
    val angharad = Character(1, "Angharad")
    characterDAO.insert(angharad)
    sentenceDAO.insert(Sentence(1, "Elle est où la poubellette ?", angharad.id))
    Future.successful(Ok("db was initialized"))
  }

  def index = Action.async { implicit request =>
    characterDAO.all().map { r => Ok(toJson(r)) }
  }

}