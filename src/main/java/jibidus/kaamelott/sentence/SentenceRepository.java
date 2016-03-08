package jibidus.kaamelott.sentence;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "sentences", path = "sentences")
public interface SentenceRepository extends PagingAndSortingRepository<Sentence, Long> {

}
