package es.zed.domain.input;

import es.zed.dto.response.AbilityResponseDto;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.respmodel.ReqRespModel;
import org.springframework.http.ResponseEntity;
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
  Mono<ResponseEntity<ReqRespModel<PokemonResponseDto>>> getPokemon();

  /**
   * Get ability.
   *
   * @param nid nid.
   * @param auth auth.
   * @return response.
   */
  ResponseEntity<ReqRespModel<AbilityResponseDto>> getAbility(final String nid, final String auth);

}
