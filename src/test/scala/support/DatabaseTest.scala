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
import me.archdev.restapi.utils.Migration

trait DatabaseTest extends Suite with Migration with BeforeAndAfter {
  protected val log: LoggingAdapter = NoLogging

  import driver.api._

  reloadSchema()

  before {
    Await.ready(db.run(characters.delete), 10.seconds)
  }

}
