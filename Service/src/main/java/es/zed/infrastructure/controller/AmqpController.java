package es.zed.infrastructure.controller;

import es.zed.common.AbstractEvent;
import es.zed.controller.AbstractAmqpController;
import es.zed.domain.input.PokeDbRabbitHandlerPort;
import es.zed.pokeapi.PokeCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * Amqp controller.
 */
@Slf4j
@RestController
public class AmqpController extends AbstractAmqpController<AbstractEvent<?>> {

  /**
   * PokeDb handler port.
   */
  private final PokeDbRabbitHandlerPort handler;

  /**
   * Constructor.
   *
   * @param requestTemplate template.
   * @param producerExchangeName producer name.
   * @param handler handler.
   */
  public AmqpController(RabbitTemplate requestTemplate, @Value("${producer.exchange.name}") String producerExchangeName,
      PokeDbRabbitHandlerPort handler) {
    super(requestTemplate, producerExchangeName);
    this.handler = handler;
  }

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
