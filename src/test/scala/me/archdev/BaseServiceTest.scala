package me.archdev

import scala.concurrent.Await
import scala.concurrent.duration._

import org.scalatest._

import akka.event.LoggingAdapter
import akka.event.NoLogging
import akka.http.scaladsl.testkit.ScalatestRouteTest
import me.archdev.restapi.http.HttpService
import me.archdev.restapi.models._
import me.archdev.restapi.utils.Migration

trait BaseServiceTest extends WordSpec with Matchers with ScalatestRouteTest with HttpService with Migration {
  protected val log: LoggingAdapter = NoLogging

  import driver.api._

  val testUsers = Seq(
    UserEntity(Some(1), "Arhelmus", "test"),
    UserEntity(Some(2), "Arch", "test"),
    UserEntity(Some(3), "Hierarh", "test"))

  val testCharacters = Seq(
    Character(1, "Arthur"),
    Character(2, "Perceval"),
    Character(3, "Karadoc"))

  reloadSchema()
  Await.result(db.run(users ++= testUsers), 10.seconds)

  Await.result(db.run(characters.delete), 10.seconds)
  Await.result(db.run(characters ++= testCharacters), 10.seconds)
}
