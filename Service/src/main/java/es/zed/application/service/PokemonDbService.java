package es.zed.application.service;

import es.zed.domain.input.PokeDbInputPort;
import es.zed.domain.output.api.PokeDbOutputPort;
import es.zed.domain.output.object.PokemonObject;
import es.zed.dto.PokemonDto;
import es.zed.dto.response.AbilityResponseDto;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.infrastructure.adapter.PokemonRepositoryAdapter;
import es.zed.shared.utils.Constants;
import es.zed.utils.CustomObjectMapper;
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
   * Mapper.
   */
  private final CustomObjectMapper mapper;

  /**
   * Poke db output port.
   */
  private final PokeDbOutputPort pokeDbOutputPort;

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
  public AbilityResponseDto getAbility(final String nid, final String auth) {
    Map<String, String> replacements = new HashMap<>();
    replacements.put(Constants.NID_URL_FILTER, nid);

    return pokeDbOutputPort.doCallGetPokemon(mapper.mapUrl(replacements,
        basePath.concat(Constants.POKE_DB_POKEMON_NID)), auth);
  }

}
