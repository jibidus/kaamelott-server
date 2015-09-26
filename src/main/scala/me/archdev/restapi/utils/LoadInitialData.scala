package me.archdev.restapi.utils

import scala.concurrent.Await
import scala.io.Source

import org.flywaydb.core.Flyway
import org.json4s._
import org.json4s.jackson.JsonMethods._

import akka.event.LoggingAdapter
import me.archdev.restapi.http.HttpService
import me.archdev.restapi.models._
import me.archdev.restapi.models.CharacterTable._

trait LoadInitialData extends DatabaseConfig {
  import driver.api._

  def loadInitialData() = {
    val source = Source.fromInputStream(getClass.getResourceAsStream("/db/initial_data/Characters.json"))
    val lines = try source.mkString finally source.close()

    implicit val formats = DefaultFormats
    val json = parse(lines)
    val new_characters = json.extract[Seq[Character]]

    println(new_characters.size + " character(s) found")

    db.run(characters ++= new_characters)
  }

}