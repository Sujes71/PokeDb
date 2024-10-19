package es.zed.domain.output.repository;

import es.zed.domain.output.entity.PokemonEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Pokemon repository.
 */
@Repository
public interface PokemonRepository extends ReactiveCrudRepository<PokemonEntity, String> {

}
