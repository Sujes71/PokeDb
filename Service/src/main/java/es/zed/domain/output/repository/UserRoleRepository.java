package es.zed.domain.output.repository;

import es.zed.domain.output.entity.UserRoleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * user role repository.
 */
@Repository
public interface UserRoleRepository extends ReactiveCrudRepository<UserRoleEntity, Long> {

  /**
   * Find username.
   *
   * @param username username.
   * @return flux.
   */
  Flux<UserRoleEntity> findByUsername(String username);
}

