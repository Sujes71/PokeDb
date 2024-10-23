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
   * @param auth auth.
   * @return response.
   */
  AbilityResponseDto doCallGetInternalPokemon(final String url, final String auth);
}
