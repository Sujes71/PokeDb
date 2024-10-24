package es.zed.application.handler;

import es.zed.domain.input.PokeDbKafkaHandlerPort;
import es.zed.domain.output.object.PokemonObject;
import es.zed.infrastructure.adapter.PokemonRepositoryAdapter;
import es.zed.pokeapi.PokeUpdatedEvent;
import es.zed.utils.CustomObjectMapper;
import org.springframework.stereotype.Service;

/**
 * PokeDb kafka event handler.
 */
@Service
public class PokeDbKafkaEventHandler implements PokeDbKafkaHandlerPort {

  /**
   * Pokemon repository adapter.
   */
  private final PokemonRepositoryAdapter pokemonRepositoryAdapter;

  /**
   * Mapper.
   */
  private final CustomObjectMapper mapper;

  /**
   * Constructor.
   *
   * @param pokemonRepositoryAdapter adapter.
   * @param mapper mapper.
   */
  public PokeDbKafkaEventHandler(PokemonRepositoryAdapter pokemonRepositoryAdapter, CustomObjectMapper mapper) {
    this.pokemonRepositoryAdapter = pokemonRepositoryAdapter;
    this.mapper = mapper;
  }

  /**
   * Handle poke updated event.
   *
   * @param pokeUpdatedEvent poke updated event.
   */
  @Override
  public void handlePokeUpdatedEvent(PokeUpdatedEvent pokeUpdatedEvent) {
    PokemonObject pokemonObject = mapper.convertValue(pokeUpdatedEvent.getBody(), PokemonObject.class);
    pokemonRepositoryAdapter.update(pokemonObject);
  }
}
