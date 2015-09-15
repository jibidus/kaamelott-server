package me.archdev.restapi.services

import me.archdev.restapi.models.CharacterEntity
import org.mindrot.jbcrypt.BCrypt
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import me.archdev.restapi.models.db.CharacterTable

object CharactersService extends CharactersService

trait CharactersService extends CharacterTable {

  import driver.api._

  def getCharacters(): Future[Seq[CharacterEntity]] = db.run(characters.result)

}