package controllers

import models._
import play.api._
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.Play.current
import play.api.mvc.BodyParsers._
import play.api.libs.json.Json
import play.api.libs.json.Json._

object CharacterController extends Controller {

  implicit val character_format = Json.format[Character]

  def init = DBAction { implicit rs =>
    val angharad = Character(1, "Angharad")
    Characters.query.insert(angharad)
    Sentences.query.insert(Sentence(1, "Elle est où la poubellette ?", angharad.id))
    Ok("db was initialized with 2 characters")
  }

  def index = DBAction { implicit rs =>
    Ok(toJson(Characters.query.list))
  }

}