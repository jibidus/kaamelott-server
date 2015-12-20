package jibidus.kaamelott.server.support

import scala.concurrent.Await
import scala.concurrent.duration._

import org.scalatest.BeforeAndAfter
import org.scalatest.Matchers
import org.scalatest.Suite

import akka.event.LoggingAdapter
import akka.event.NoLogging
import jibidus.kaamelott.server.models.Character
import jibidus.kaamelott.server.models.CharacterTable.characters
import jibidus.kaamelott.server.utils.Migration

trait DatabaseTest extends Suite with Migration with BeforeAndAfter {
  protected val log: LoggingAdapter = NoLogging

  before {
    reloadSchema()
  }

}
