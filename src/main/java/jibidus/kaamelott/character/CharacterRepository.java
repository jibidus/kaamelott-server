package jibidus.kaamelott.character;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "characters", path = "characters")
public interface CharacterRepository extends PagingAndSortingRepository<jibidus.kaamelott.character.Character, String> {

}
