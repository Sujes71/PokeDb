package es.zed.infrastructure.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.zed.common.AbstractEvent;
import es.zed.config.AbstractKafkaController;
import es.zed.domain.input.PokeDbKafkaHandlerPort;
import es.zed.pokeapi.PokeUpdatedEvent;
import es.zed.repository.EventIdRepository;
import es.zed.repository.model.EventId;
import es.zed.shared.utils.Constants;
import es.zed.utils.CustomObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kafka controller.
 */
@RestController
public class KafkaController extends AbstractKafkaController<AbstractEvent<?>> {

  /**
   * Custom object mapper.
   */
  private final CustomObjectMapper customObjectMapper;

  /**
   * Poke db kafka handler port.
   */
  private final PokeDbKafkaHandlerPort handler;

  /**
   * Event id repository.
   */
  private final EventIdRepository repository;

  /**
   * Constructor.
   *
   * @param template template.
   * @param customObjectMapper mapper.
   * @param handler handler.
   * @param repository event repository.
   */
  protected KafkaController(KafkaTemplate<String, AbstractEvent<?>> template, CustomObjectMapper customObjectMapper,
      PokeDbKafkaHandlerPort handler, EventIdRepository repository) {
    super(template);
    this.customObjectMapper = customObjectMapper;
    this.handler = handler;
    this.repository = repository;
  }

  /**
   * Consume Poke updated event.
   *
   * @param message message.
   * @throws JsonProcessingException ex.
   */
  @KafkaListener(topics = Constants.POKE_UPDATED_EVENT, groupId = Constants.KAFKA_GROUP_ID)
  public void consume(String message) throws JsonProcessingException {
    PokeUpdatedEvent pokeUpdatedEvent = customObjectMapper.readValue(message, PokeUpdatedEvent.class);
    handler.handlePokeUpdatedEvent(pokeUpdatedEvent);
    repository.save(new EventId(pokeUpdatedEvent.getMessageId(), pokeUpdatedEvent.getContext(),
        pokeUpdatedEvent.getTypeId(), Constants.KAFKA_MANAGER));
  }

}
