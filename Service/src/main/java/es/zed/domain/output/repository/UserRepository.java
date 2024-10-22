package es.zed.domain.output.repository;

import es.zed.domain.output.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * user repository.
 */
public interface UserRepository extends ReactiveCrudRepository<UserEntity, String> {
}
