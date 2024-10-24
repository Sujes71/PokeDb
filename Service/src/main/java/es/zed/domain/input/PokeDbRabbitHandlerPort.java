package es.zed.domain.input;

import es.zed.pokeapi.PokeCreatedEvent;

/**
 * poke db handler port.
 */
public interface PokeDbRabbitHandlerPort {

  /**
   * PokeCreatedEvent handler.
   *
   * @param pokeCreatedEvent poke created event.
   */
  void handlePokeCreatedEvent(PokeCreatedEvent pokeCreatedEvent);

}
