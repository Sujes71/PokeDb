package es.zed.domain.output.repository;

import es.zed.domain.output.entity.AbilityEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Ability repository.
 */
@Repository
public interface AbilityRepository extends ReactiveCrudRepository<AbilityEntity, String> {

}
