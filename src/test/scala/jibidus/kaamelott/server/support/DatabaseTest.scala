package jibidus.kaamelott.server.support

import akka.event.{LoggingAdapter, NoLogging}
import jibidus.kaamelott.server.utils.Migration
import org.scalatest.{BeforeAndAfter, Suite}

trait DatabaseTest extends Suite with Migration with BeforeAndAfter {
  protected val log: LoggingAdapter = NoLogging

  before {
    reloadSchema()
  }

}
