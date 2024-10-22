package es.zed.domain.input;

import es.zed.dto.request.LoginRequestDto;
import es.zed.respmodel.ReqRespModel;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

/**
 * Poke login input port.
 */
public interface PokeLoginInputPort {

  /**
   * Login.
   *
   * @param requestDto request.
   * @return response.
   */
  Mono<ResponseEntity<ReqRespModel<String>>> login(LoginRequestDto requestDto);
}
