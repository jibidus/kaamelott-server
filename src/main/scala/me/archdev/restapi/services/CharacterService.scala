package me.archdev.restapi.services

import me.archdev.restapi.models.CharacterTable

object CharactersService extends CharactersService

trait CharactersService {

  def getCharacters() = CharacterTable.all()

}