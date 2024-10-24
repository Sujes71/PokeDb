package es.zed.domain.output.repository;

import es.zed.domain.output.entity.PokemonEntity;
import java.util.UUID;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Pokemon repository.
 */
@Repository
public interface PokemonRepository extends ReactiveCrudRepository<PokemonEntity, UUID> {

  /**
   * Find by id.
   *
   * @param nativeId native id.
   * @return pokemon.
   */
  @Query("SELECT * FROM Pokemon WHERE id = :nativeId")
  Mono<PokemonEntity> findById(String nativeId);

  /**
   * Update name by native id.
   *
   * @param nativeId native id.
   * @param name name.
   * @return pokemon.
   */
  @Query("UPDATE Pokemon SET name = :name WHERE id = :nativeId RETURNING *")
  Mono<PokemonEntity> updateNameByNativeId(String nativeId, String name);
}
