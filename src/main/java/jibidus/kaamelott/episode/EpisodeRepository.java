package jibidus.kaamelott.episode;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "episodes", path = "episodes")
public interface EpisodeRepository extends PagingAndSortingRepository<Episode, EpisodeId> {

}
