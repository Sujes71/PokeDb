package es.zed.infrastructure.controller;

import es.zed.domain.input.PokeLoginInputPort;
import es.zed.dto.request.LoginRequestDto;
import es.zed.respmodel.ReqRespModel;
import es.zed.security.PokeAuthentication;
import es.zed.shared.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Poke db controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.BASE_URL)
public class PokeLoginController {

  /**
   * PokeDb input port.
   */
  private final PokeLoginInputPort pokeLoginInputPort;

  /**
   * Login controller.
   *
   * @param loginRequestDto request.
   * @return login response data.
   */
  @PostMapping(path = Constants.POKE_DB_LOGIN, produces = MediaType.APPLICATION_JSON_VALUE)
  private Mono<ResponseEntity<ReqRespModel<String>>> login(@RequestBody LoginRequestDto loginRequestDto) {
    return pokeLoginInputPort.login(loginRequestDto);
  }

  /**
   * Login controller.
   *
   * @param auth auth.
   * @return response.
   */
  @PostMapping(path = Constants.POKE_DB_LOGOUT, produces = MediaType.APPLICATION_JSON_VALUE)
  private Mono<ResponseEntity<ReqRespModel<String>>> logout(final PokeAuthentication auth) {
    return pokeLoginInputPort.logout(auth);
  }


}
