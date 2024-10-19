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
   * Find all pokemon.
   *
   * @return findAll.
   */
  public Flux<PokemonEntity> findAll() {
    return pokemonRepository.findAll();
  }

}
