package es.zed.application.service;

import es.zed.domain.input.PokeLoginInputPort;
import es.zed.domain.output.entity.UserRoleEntity;
import es.zed.domain.output.repository.UserRepository;
import es.zed.domain.output.repository.UserRoleRepository;
import es.zed.dto.request.LoginRequestDto;
import es.zed.respmodel.ReqRespModel;
import es.zed.security.JwtService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Poke login service.
 */
@Service
public class PokeLoginService implements PokeLoginInputPort {

  /**
   * user repository.
   */
  private final UserRepository userRepository;

  /**
   * user role repository.
   */
  private final UserRoleRepository userRoleRepository;

  /**
   * jwt service.
   */
  private final JwtService jwtService;

  /**
   * Constructor.
   * @param userRepository user repository.
   * @param userRoleRepository user role repository.
   * @param jwtService jwtService.
   */
  public PokeLoginService(UserRepository userRepository, UserRoleRepository userRoleRepository,
      JwtService jwtService) {
    this.userRepository = userRepository;
    this.userRoleRepository = userRoleRepository;
    this.jwtService = jwtService;
  }

  /**
   * Login.
   *
   * @param requestDto request.
   * @return response.
   */
  @Override
  public Mono<ResponseEntity<ReqRespModel<String>>> login(LoginRequestDto requestDto) {
    return userRepository.findById(requestDto.getUsername())
        .flatMap(user -> {
          if (!user.getPassword().equals(requestDto.getPassword())) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ReqRespModel<String>(null, "Invalid Credentials")));
          }

          return userRoleRepository.findByUsername(user.getUsername())
              .collectList()
              .flatMap(userRoles -> {
                Map<String, Object> claims = new HashMap<>();
                claims.put("roles", userRoles.stream()
                    .map(UserRoleEntity::getRoleId)
                    .toList());

                String token = jwtService.generateToken(user.getUsername(), claims);

                return Mono.just(ResponseEntity.ok(new ReqRespModel<String>(token, null)));
              });
        })
        .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ReqRespModel<String>(null, "Invalid Username"))));
  }


}
