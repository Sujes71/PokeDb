package es.zed.infrastructure.adapter;

import es.zed.domain.output.entity.PokemonEntity;
import es.zed.domain.output.object.PokemonObject;
import es.zed.domain.output.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Pokemon repository adapter.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PokemonRepositoryAdapter {

  /**
   * pokemon repository.
   */
  private final PokemonRepository pokemonRepository;

  /**
   * Save pokemon.
   *
   * @param pokemonObject pokemon object.
   */
  public void save(PokemonObject pokemonObject) {
    PokemonEntity pokemon = PokemonEntity.fromObjectToEntity(pokemonObject);
    pokemonRepository.save(pokemon)
        .doOnSuccess(savedPokemon -> log.info("Pokemon saved correctly {}", savedPokemon))
        .onErrorResume(ex -> {
          log.error("Error saving pokemon: {}", ex.getMessage());
          return Mono.empty();
        })
        .subscribe();
  }

  /**
   * Update pokemon.
   *
   * @param pokemonObject pokemon object.
   */
  public void update(PokemonObject pokemonObject) {
    if (pokemonObject.getId() == null) {
      log.error("Cannot update Pokemon: ID is null");
      return;
    }

    pokemonRepository.findById(pokemonObject.getId())
        .flatMap(existingPokemon -> pokemonRepository.updateNameByNativeId(pokemonObject.getId(), pokemonObject.getName()))
        .doOnSuccess(updatedPokemon -> {
          if (updatedPokemon != null) {
            log.info("Pokemon updated successfully: {}", updatedPokemon);
          }
        })
        .doOnError(ex -> log.error("Error updating pokemon: {}", ex.getMessage()))
        .switchIfEmpty(Mono.fromRunnable(() -> log.warn("No Pokemon found with ID: {}", pokemonObject.getId())))
        .subscribe();
  }

  /**
   * Find all pokemon.
   *
   * @return findAll.
   */
  public Flux<PokemonEntity> findAll() {
    return pokemonRepository.findAll();
  }

}
