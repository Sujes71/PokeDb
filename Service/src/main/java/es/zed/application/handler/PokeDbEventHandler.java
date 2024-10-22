package es.zed.application.handler;

import es.zed.domain.input.PokeDbHandlerPort;
import es.zed.domain.output.object.PokemonObject;
import es.zed.infrastructure.adapter.PokemonRepositoryAdapter;
import es.zed.pokeapi.PokeCreatedEvent;
import es.zed.utils.CustomObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Poke db event handler.
 */
@Service
@RequiredArgsConstructor
public class PokeDbEventHandler implements PokeDbHandlerPort {

  /**
   * Pokemon repository adapter.
   */
  private final PokemonRepositoryAdapter pokemonRepositoryAdapter;

  /**
   * Mapper.
   */
  private final CustomObjectMapper mapper;


  /**
   * Handle poke event.
   *
   * @param pokeCreatedEvent event.
   */
  @Override
  public void handlePokeCreatedEvent(PokeCreatedEvent pokeCreatedEvent) {
    PokemonObject pokemonObject = mapper.convertValue(pokeCreatedEvent.getBody(), PokemonObject.class);
    pokemonRepositoryAdapter.save(pokemonObject);
  }
}
