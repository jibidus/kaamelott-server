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

trait Migration extends Config with DatabaseConfig {

  private val flyway = new Flyway()
  flyway.setDataSource(databaseUrl, databaseUser, databasePassword)

  def migrate() = {
    flyway.migrate()
  }

  def reloadSchema() = {
    flyway.clean()
    flyway.migrate()
  }

}
