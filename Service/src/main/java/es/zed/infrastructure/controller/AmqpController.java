package es.zed.infrastructure.controller;

import es.zed.controller.AbstractAmqpController;
import es.zed.domain.input.PokeDbHandlerPort;
import es.zed.event.common.AbstractEvent;
import es.zed.event.pokeapi.PokeCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * Amqp controller.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class AmqpController extends AbstractAmqpController<AbstractEvent> {

  /**
   * PokeDb handler port.
   */
  private final PokeDbHandlerPort handler;

  /**
   * Poke created event consumer.
   *
   * @param event event.
   */
  public void consume(PokeCreatedEvent event) {
    log.info("Event {} is being consumed from {}.", event.getTypeId(), event.getContext());
    handler.handlePokeCreatedEvent(event);
  }
}
