package es.zed.infrastructure.controller;

import es.zed.config.JwtBearerToken;
import es.zed.domain.input.PokeDbInputPort;
import es.zed.dto.response.AbilityResponseDto;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.shared.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
  private Mono<PokemonResponseDto> getPokemon() {
    return pokeDbInputPort.getPokemon();
  }

  /**
   * Method to get the pokemon by id.
   *
   * @param nid nid.
   * @param auth auth.
   * @return the pokemon.
   */
  @GetMapping(path = Constants.POKE_DB_ABILITY_NID, produces = MediaType.APPLICATION_JSON_VALUE)
  private AbilityResponseDto getAbility(@PathVariable final String nid,
      @RequestHeader(name = "Authorization") String auth) {
    return pokeDbInputPort.getAbility(nid, auth);
  }
}
