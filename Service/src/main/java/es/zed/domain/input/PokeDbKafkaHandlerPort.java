package es.zed.domain.input;

import es.zed.pokeapi.PokeUpdatedEvent;

/**
 * Poke db handler port.
 */
public interface PokeDbKafkaHandlerPort {

  /**
   * PokeUpdatedEvent handler.
   *
   * @param pokeUpdatedEvent poke updated event.
   */
  void handlePokeUpdatedEvent(PokeUpdatedEvent pokeUpdatedEvent);
}
