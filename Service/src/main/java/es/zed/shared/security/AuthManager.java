package es.zed.shared.security;

import es.zed.config.JwtBearerToken;
import es.zed.domain.output.entity.UserEntity;
import es.zed.domain.output.entity.UserRoleEntity;
import es.zed.domain.output.repository.UserRepository;
import es.zed.domain.output.repository.UserRoleRepository;
import es.zed.security.JwtService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Auth manager.
 */
@Slf4j
@Component
public class AuthManager implements ReactiveAuthenticationManager {

  /**
   * jwt service.
   */
  private final JwtService jwtService;

  /**
   * user repository.
   */
  private final UserRepository userRepository;

  /**
   * user role repository.
   */
  private final UserRoleRepository userRoleRepository;

  /**
   * Constructor.
   *
   * @param jwtService jwt service.
   * @param userRepository users repository.
   * @param userRoleRepository userRoles repository.
   */
  public AuthManager(JwtService jwtService, UserRepository userRepository, UserRoleRepository userRoleRepository) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
    this.userRoleRepository = userRoleRepository;
  }

  /**
   * Authenticate.
   *
   * @param authentication authentication.
   * @return authentication.
   */
  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    return Mono.justOrEmpty(authentication)
        .cast(JwtBearerToken.class)
        .flatMap(auth -> {
          String userName = auth.getPrincipal();
          Mono<UserEntity> foundUser = userRepository.findById(userName);
          Flux<UserRoleEntity> userRolesFlux = userRoleRepository.findByUsername(userName);

          return foundUser.flatMap(u -> {
            if (u.getUsername() == null) {
              return Mono.error(new IllegalArgumentException("User not found in auth manager."));
            }

            return userRolesFlux.collectList().flatMap(ur -> {
              List<GrantedAuthority> authorities = ur.stream()
                  .map(role -> new SimpleGrantedAuthority(role.getRoleId()))
                  .collect(Collectors.toList());

              return Mono.just(new UsernamePasswordAuthenticationToken(
                  u.getUsername(),
                  null,
                  authorities
              ));
            });
          });
        });
  }
}
