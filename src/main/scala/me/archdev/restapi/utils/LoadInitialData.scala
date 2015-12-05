package me.archdev.restapi.utils

import scala.concurrent._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.io.Source

import org.json4s._
import org.json4s.native.JsonMethods._

import akka.event.LoggingAdapter
import me.archdev.restapi.models._
import me.archdev.restapi.models.CharacterTable._

trait LoadInitialData extends DatabaseConfig {
  import driver.api._

  def loadInitialData(): Option[Future[Option[Boolean]]] = {
    val count = Await.result(CharacterTable.count(), 10.seconds)
    if (count != 0) return None

    val source = Source.fromInputStream(getClass.getResourceAsStream("/db/initial_data/Characters.json"))
    val lines = try source.mkString finally source.close()

    // TODO Extract json with future ?
    implicit val formats = DefaultFormats
    val json = parse(lines)
    val new_characters = json.extract[Seq[Character]]
    println("Load initial data with " + new_characters.size + " character(s)")

    Some(Future {
      for {
        charactersLoading <- Some(db.run(characters ++= new_characters))
      } yield true
    })
  }
}