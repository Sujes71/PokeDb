package es.zed.application.service;

import es.zed.domain.input.PokeLoginInputPort;
import es.zed.domain.output.repository.RolesRepository;
import es.zed.domain.output.repository.UserRepository;
import es.zed.dto.request.LoginRequestDto;
import es.zed.respmodel.ReqRespModel;
import es.zed.security.JwtBearerToken;
import es.zed.security.JwtService;
import es.zed.security.PokeAuthentication;
import es.zed.utils.UuidUtils;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import org.springframework.data.redis.core.ReactiveValueOperations;
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
   * RedisOps.
   */
  private final ReactiveValueOperations<String, String> redisOps;

  /**
   * Constructor.
   *
   * @param userRepository user repository.
   * @param rolesRepository roles repository.
   * @param jwtService jwtService.
   * @param redisOps redisOps.
   */
  public PokeLoginService(UserRepository userRepository, RolesRepository rolesRepository,
      JwtService jwtService, ReactiveValueOperations<String, String> redisOps) {
    this.userRepository = userRepository;
    this.rolesRepository = rolesRepository;
    this.jwtService = jwtService;
    this.redisOps = redisOps;
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

          return rolesRepository.findById(user.getRoleId())
              .flatMap(role -> {
                LinkedList<String> authorities = new LinkedList<>();
                authorities.add(role.getId());

                JwtBearerToken jwtBearerToken = new JwtBearerToken(
                    UuidUtils.newUuid(),
                    ZonedDateTime.ofInstant(Instant.now().plus(1, ChronoUnit.HOURS), ZoneOffset.UTC),
                    user.getUsername(),
                    authorities
                );

                String token = jwtService.createJwtFromSpec(jwtBearerToken);

                return redisOps.set(user.getUsername(), jwtBearerToken.getId(), Duration.ofHours(1))
                    .thenReturn(ResponseEntity.ok(new ReqRespModel<>(token, "Success")));
              });
        })
        .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ReqRespModel<>(null, "Invalid Username"))));
  }

  /**
   * Logout.
   *
   * @param auth auth.
   * @return logout response.
   */
  @Override
  public Mono<ResponseEntity<ReqRespModel<String>>> logout(final PokeAuthentication auth) {
    return redisOps.delete((String) auth.getPrincipal())
        .map(deleted -> {
          if (Boolean.TRUE.equals(deleted)) {
            return ResponseEntity.ok(new ReqRespModel<>("Logout successful", "Success"));
          } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ReqRespModel<>(null, "Token not found or already invalidated"));
          }
        });
  }
}
