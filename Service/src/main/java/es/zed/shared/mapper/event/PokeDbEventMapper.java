package es.zed.shared.mapper.event;

import es.zed.common.utils.EventMapper;
import es.zed.common.utils.UuidUtils;
import es.zed.dto.response.AbilityResponseDto;
import es.zed.event.pokedb.AbilityCreatedEvent;
import es.zed.event.pokedb.AbilityCreatedEventBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Poke db event mapper.
 */
@Component
@RequiredArgsConstructor
public class PokeDbEventMapper implements EventMapper<AbilityResponseDto, AbilityCreatedEvent> {

  /**
   * Build ability created event.
   *
   * @param object object.
   * @return event.
   */
  @Override
  public AbilityCreatedEvent buildEvent(AbilityResponseDto object) {
    return AbilityCreatedEvent
        .builder()
        .pokemonId(object.getId())
        .creationTs(System.currentTimeMillis())
        .typeId(AbilityCreatedEvent.TYPE_ID)
        .messageId(UuidUtils.newUuid())
        .origin(AbilityCreatedEvent.CONTEXT)
        .body(AbilityCreatedEventBody
            .builder()
            .name(object.getAbilities().get(0).getAbility().getName())
            .url(object.getAbilities().get(0).getAbility().getUrl())
            .build())
        .build();
  }
}
