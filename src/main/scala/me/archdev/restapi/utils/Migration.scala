package me.archdev.restapi.utils

import scala.concurrent.Await
import scala.io.Source

import org.flywaydb.core.Flyway
import org.json4s._
import org.json4s.jackson.JsonMethods._

import akka.event.LoggingAdapter
import me.archdev.restapi.http.HttpService
import me.archdev.restapi.models._
import scala.concurrent.Await
import scala.concurrent.duration._

trait Migration extends Config with DatabaseConfig with HttpService {
  import driver.api._

  private val flyway = new Flyway()
  flyway.setDataSource(databaseUrl, databaseUser, databasePassword)

  def migrate() = {
    flyway.migrate()
  }

  def reloadSchema() = {
    flyway.clean()
    flyway.migrate()
  }

  def loadInitialData() = {
    val source = Source.fromInputStream(getClass.getResourceAsStream("/Characters.json"))
    val lines = try source.mkString finally source.close()

    implicit val formats = DefaultFormats
    val json = parse(lines)
    val new_characters = json.extract[Seq[Character]]
    println(new_characters.size + " character(s) found")

    db.run(characters ++= new_characters)
  }

}
