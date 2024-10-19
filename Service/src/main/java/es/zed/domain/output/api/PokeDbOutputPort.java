package es.zed.domain.output.api;

import es.zed.dto.response.AbilityResponseDto;

/**
 * PokeDbOutputPort.
 */
public interface PokeDbOutputPort {

  /**
   * Do call get pokemon.
   *
   * @param url url.
   * @return response.
   */
  AbilityResponseDto doCallGetPokemon(final String url);
}
