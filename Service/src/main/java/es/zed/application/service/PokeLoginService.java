package es.zed.application.service;

import es.zed.config.JwtBearerToken;
import es.zed.domain.input.PokeLoginInputPort;
import es.zed.domain.output.repository.RolesRepository;
import es.zed.domain.output.repository.UserRepository;
import es.zed.dto.request.LoginRequestDto;
import es.zed.respmodel.ReqRespModel;
import es.zed.security.JwtService;
import es.zed.security.enums.AccessRoleEnum;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
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
   * roles repository.
   */
  private final RolesRepository rolesRepository;

  /**
   * jwt service.
   */
  private final JwtService jwtService;

  /**
   * Constructor.
   * @param userRepository user repository.
   * @param rolesRepository roles repository.
   * @param jwtService jwtService.
   */
  public PokeLoginService(UserRepository userRepository, RolesRepository rolesRepository,
      JwtService jwtService) {
    this.userRepository = userRepository;
    this.rolesRepository = rolesRepository;
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
          // Verificación de la contraseña
          if (!user.getPassword().equals(requestDto.getPassword())) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ReqRespModel<String>(null, "Invalid Credentials")));
          }

          return rolesRepository.findById(user.getRoleId())
              .flatMap(role -> {
                String token = jwtService.createJwtFromSpec(new JwtBearerToken(
                    null,
                    ZonedDateTime.ofInstant(Instant.now().plus(1, ChronoUnit.HOURS), ZoneOffset.UTC),
                    user.getUsername(),
                    user.getUsername(),
                    AccessRoleEnum.valueOf(role.getId()),
                    new LinkedList<>()
                ));

                return Mono.just(ResponseEntity.ok(new ReqRespModel<String>(token, null)));
              });
        })
        .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ReqRespModel<String>(null, "Invalid Username"))));
  }
}
