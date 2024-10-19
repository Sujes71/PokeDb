package es.zed.infrastructure.adapter;

import es.zed.domain.output.entity.AbilityEntity;
import es.zed.domain.output.object.AbilityObject;
import es.zed.domain.output.repository.AbilityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Ability repository adapter.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AbilityRepositoryAdapter {

  /**
   * ability repository.
   */
  private final AbilityRepository abilityRepository;

  /**
   * Save ability.
   *
   * @param abilityObject ability object.
   */
  public void save(AbilityObject abilityObject) {
    AbilityEntity ability = AbilityEntity.fromObjectToEntity(abilityObject);
    abilityRepository.save(ability)
        .doOnSuccess(a -> log.info("Ability saved correctly {}", a))
        .onErrorResume(ex -> {
          log.error("Error saving ability: {}", ex.getMessage());
          return Mono.empty();
        })
        .subscribe();
  }

}
