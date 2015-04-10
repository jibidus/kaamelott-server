package controllers

import play.api.libs.iteratee.Enumerator
import play.api.libs.json.{ JsObject, Json }
import play.api.mvc.{ BodyParsers, Action, Controller }
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import reactivemongo.bson.BSONDocument
import play.modules.reactivemongo.json.BSONFormats._
import play.modules.reactivemongo.json.ImplicitBSONHandlers._

object Sentence extends Controller with MongoController {

  def collection = db.collection[JSONCollection]("sentences")

  def init = Action.async { implicit request =>
    val obj1 = Json.obj(
      "uid" -> 1,
      "text" -> "Hé ben, si un jour j’oublie que je suis bonniche, vous serez gentils de me le rappeler !",
      "character" -> "Caius",
      "episode" -> "s01e12")
    val obj2 = Json.obj(
      "uid" -> 2,
      "text" -> "Si Monsieur et Madame préfèrent s\'envoyer des fions dans l\'intimité, je peux aussi me retirer.",
      "character" -> "Arthur",
      "episode" -> "s03e23")

    collection.bulkInsert(Enumerator.enumerate(List(obj1, obj2)))
      .map(i => Ok("db was initialized with sentences"))
  }

  def list = Action.async { implicit request =>
    collection.find(Json.obj())
      .cursor[JsObject]
      .collect[List]()
      .map(sentence => Ok(Json.toJson(sentence)))
  }

}