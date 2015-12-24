package jibidus.kaamelott.server.utils

import jibidus.kaamelott.server.models.CharacterTable._
import jibidus.kaamelott.server.models._
import org.json4s._
import org.json4s.native.JsonMethods._
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.io.Source

trait LoadInitialData extends DatabaseConfig {
  import driver.api._

  val logger = LoggerFactory.getLogger(this.getClass)

  def loadInitialData(): Option[Future[Option[Boolean]]] = {
    val count = Await.result(CharacterTable.count(), 10.seconds)
    if (count != 0) return None

    val source = Source.fromInputStream(getClass.getResourceAsStream("/db/initial_data/Characters.json"))
    val lines = try source.mkString finally source.close()

    // TODO Extract json with future ?
    implicit val formats = DefaultFormats
    val json = parse(lines)
    val new_characters = json.extract[Seq[Character]]
    logger.info("Load initial data with {} character(s)", new_characters.size)

    Some(Future {
      for {
        charactersLoading <- Some(db.run(characters ++= new_characters))
      } yield true
    })
  }
}