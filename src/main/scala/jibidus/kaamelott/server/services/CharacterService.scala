package jibidus.kaamelott.server.services

import jibidus.kaamelott.server.models.CharacterTable

object CharactersService extends CharactersService

trait CharactersService {

  def getCharacters() = CharacterTable.all()

}