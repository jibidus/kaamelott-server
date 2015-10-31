package support

import scala.concurrent.Await
import scala.concurrent.duration._

import org.scalatest.BeforeAndAfter
import org.scalatest.Matchers
import org.scalatest.Suite

import akka.event.LoggingAdapter
import akka.event.NoLogging
import me.archdev.restapi.models.Character
import me.archdev.restapi.models.CharacterTable.characters
import me.archdev.restapi.models.UserEntity
import me.archdev.restapi.models.UserEntityTable
import me.archdev.restapi.utils.Migration

trait DatabaseTest extends Suite with Migration with BeforeAndAfter with UserEntityTable {
  protected val log: LoggingAdapter = NoLogging

  import driver.api._

  val testUsers = Seq(
    UserEntity(Some(1), "Arhelmus", "test"),
    UserEntity(Some(2), "Arch", "test"),
    UserEntity(Some(3), "Hierarh", "test"))

  reloadSchema()

  before {
    Await.result(db.run(users ++= testUsers), 10.seconds)

    Await.ready(db.run(characters.delete), 10.seconds)
  }

}
