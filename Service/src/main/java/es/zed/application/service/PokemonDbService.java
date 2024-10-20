package es.zed.application.service;

import es.zed.common.utils.CustomObjectMapper;
import es.zed.domain.input.PokeDbInputPort;
import es.zed.domain.output.api.PokeDbOutputPort;
import es.zed.domain.output.object.AbilityObject;
import es.zed.domain.output.object.PokemonObject;
import es.zed.dto.PokemonDto;
import es.zed.dto.response.AbilityResponseDto;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.enums.StatusType;
import es.zed.infrastructure.adapter.AbilityRepositoryAdapter;
import es.zed.infrastructure.adapter.PokemonRepositoryAdapter;
import es.zed.infrastructure.controller.AmqpController;
import es.zed.shared.mapper.event.PokeDbEventMapper;
import es.zed.shared.utils.Constants;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Pokemon db service.
 */
@Service
@RequiredArgsConstructor
public class PokemonDbService implements PokeDbInputPort {

  /**
   * Base path.
   */
  @Value("${poke-api.baseUrl}")
  private String basePath;

  /**
   * Pokemon repository adapter.
   */
  private final PokemonRepositoryAdapter pokemonRepositoryAdapter;

  /**
   * Ability repository adapter.
   */
  private final AbilityRepositoryAdapter abilityRepositoryAdapter;

  /**
   * Mapper.
   */
  private final CustomObjectMapper mapper;

  /**
   * Poke db output port.
   */
  private final PokeDbOutputPort pokeDbOutputPort;

  /**
   * Amqp controller.
   */
  private final AmqpController amqpController;

  /**
   * Amqp controller.
   */
  private final PokeDbEventMapper eventMapper;

  /**
   * Get pokemon.
   *
   * @return response.
   */
  @Override
  public Mono<PokemonResponseDto> getPokemon() {
    Flux<PokemonObject> pokemonObject = pokemonRepositoryAdapter.findAll().map(PokemonObject::fromEntityToObject);
    return pokemonObject
        .map(pokemonEntity -> mapper.convertValue(pokemonEntity, PokemonDto.class))
        .collectList()
        .map(PokemonResponseDto::new);
  }

  /**
   * Get ability.
   *
   * @param nid nid.
   * @return response.
   */
  @Override
  public AbilityResponseDto getAbility(String nid) {
    Map<String, String> replacements = new HashMap<>();
    replacements.put(Constants.NID_URL_FILTER, nid);

    return pokeDbOutputPort.doCallGetPokemon(mapper.mapUrl(replacements,
        basePath.concat(Constants.POKE_DB_POKEMON_NID)));
  }

  /**
   * Post ability.
   *
   * @param nid nid.
   * @param status status.
   */
  @Override
  public void postAbility(final String nid, final String status) {
    if (status.equals(StatusType.GOING.name())) {
      amqpController.publish(eventMapper.buildEvent(getAbility(nid)));
      return;
    }
    AbilityResponseDto abilityResponseDto = getAbility(nid);

    this.abilityRepositoryAdapter.save(AbilityObject
        .builder()
            .name(abilityResponseDto.getAbilities().get(0).getAbility().getName())
            .url(abilityResponseDto.getAbilities().get(0).getAbility().getUrl())
        .build());
  }

}
