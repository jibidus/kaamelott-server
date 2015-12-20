package jibidus.kaamelott.server.utils

import org.flywaydb.core.Flyway

trait Migration extends DatabaseConfig {

  private val flyway = new Flyway()
  flyway.setDataSource(Config.databaseUrl, Config.databaseUser, Config.databasePassword)

  def migrate() = flyway.migrate

  def reloadSchema() = {
    flyway.clean()
    flyway.migrate()
  }

}
