package es.zed.domain.input;

import es.zed.dto.response.AbilityResponseDto;
import es.zed.dto.response.PokemonResponseDto;
import reactor.core.publisher.Mono;

/**
 * Poke db input port.
 */
public interface PokeDbInputPort {

  /**
   * Get pokemon.
   *
   * @return response.
   */
  Mono<PokemonResponseDto> getPokemon();

  /**
   * Get ability.
   *
   * @param nid nid.
   * @param auth auth.
   * @return response.
   */
  AbilityResponseDto getAbility(final String nid, final String auth);

}
