package es.zed.domain.output.repository;

import es.zed.domain.output.entity.RoleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * user role repository.
 */
@Repository
public interface RolesRepository extends ReactiveCrudRepository<RoleEntity, String> {

}

