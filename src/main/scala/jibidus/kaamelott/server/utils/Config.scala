package jibidus.kaamelott.server.utils

import com.typesafe.config.ConfigFactory

object Config {
  private val config = ConfigFactory.load()
  private val httpConfig = config.getConfig("http")
  private val databaseConfig = config.getConfig("database")

  def httpInterface = httpConfig.getString("interface")
  def httpPort = httpConfig.getInt("port")

  def databaseUrl = databaseConfig.getString("url")
  def databaseUser = databaseConfig.getString("user")
  def databasePassword = databaseConfig.getString("password")
}
