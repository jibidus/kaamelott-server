package jibidus.kaamelott.server.utils

import org.flywaydb.core.api.MigrationInfo

import scala.concurrent.Await
import scala.io.Source

import org.flywaydb.core.Flyway
import org.json4s._

import akka.event.LoggingAdapter
import jibidus.kaamelott.server.models._
import jibidus.kaamelott.server.models.CharacterTable._

trait Migration extends DatabaseConfig {

  private val flyway = new Flyway()
  flyway.setDataSource(Config.databaseUrl, Config.databaseUser, Config.databasePassword)

  def migrate() = flyway.migrate

  def reloadSchema() = {
    flyway.clean()
    flyway.migrate()
  }

}
