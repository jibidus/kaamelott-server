package me.archdev.restapi.services

import me.archdev.restapi.models.Character
import org.mindrot.jbcrypt.BCrypt
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import me.archdev.restapi.models.CharacterTable

object CharactersService extends CharactersService

trait CharactersService extends CharacterTable {

  import driver.api._

  def getCharacters(): Future[Seq[Character]] = db.run(characters.result)

}