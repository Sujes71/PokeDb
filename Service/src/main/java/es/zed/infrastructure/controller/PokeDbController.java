package es.zed.infrastructure.controller;

import es.zed.domain.input.PokeDbInputPort;
import es.zed.dto.response.AbilityResponseDto;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.respmodel.ReqRespModel;
import es.zed.security.PokeAuthentication;
import es.zed.shared.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Poke db controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.BASE_URL)
public class PokeDbController {

  /**
   * PokeDb input port.
   */
  private final PokeDbInputPort pokeDbInputPort;

  /**
   * Method to get the pokemon by id.
   *
   * @return the pokemon.
   */
  @GetMapping(path = Constants.POKE_DB_POKEMON, produces = MediaType.APPLICATION_JSON_VALUE)
  private Mono<ResponseEntity<ReqRespModel<PokemonResponseDto>>> getPokemon() {
    return pokeDbInputPort.getPokemon()
        .map(pokemonResponse -> new ReqRespModel<>(pokemonResponse, "Success"))
        .map(ResponseEntity::ok);
  }

  /**
   * Method to get the pokemon by id.
   *
   * @param nid nid.
   * @param auth auth.
   * @return the pokemon.
   */
  @GetMapping(path = Constants.POKE_DB_ABILITY_NID, produces = MediaType.APPLICATION_JSON_VALUE)
  private ResponseEntity<ReqRespModel<AbilityResponseDto>> getAbility(@PathVariable final String nid,
      final PokeAuthentication auth) {
    return ResponseEntity.ok(new ReqRespModel<>(pokeDbInputPort.getAbility(nid, auth), "Success"));
  }
}
