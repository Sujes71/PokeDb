package es.zed.shared.security;

import es.zed.config.PokeAuthentication;
import es.zed.domain.output.repository.RolesRepository;
import es.zed.domain.output.repository.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Auth manager.
 */
@Slf4j
@Component
public class AuthManager implements ReactiveAuthenticationManager {

  /**
   * user repository.
   */
  private final UserRepository userRepository;

  /**
   * roles repository.
   */
  private final RolesRepository rolesRepository;

  /**
   * Constructor.
   *
   * @param userRepository users repository.
   * @param rolesRepository roles repository.
   */
  public AuthManager(UserRepository userRepository, RolesRepository rolesRepository) {
    this.userRepository = userRepository;
    this.rolesRepository = rolesRepository;
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
        .cast(PokeAuthentication.class)
        .flatMap(auth -> {
          String userName = auth.getUsername();
          return userRepository.findById(userName)
              .flatMap(user -> {
                if (user.getUsername() == null) {
                  return Mono.error(new IllegalArgumentException("User not found in auth manager."));
                }

                return rolesRepository.findById(user.getRoleId())
                    .flatMap(role -> {
                      List<GrantedAuthority> authorities = List.of(
                          new SimpleGrantedAuthority(role.getId())
                      );

                      return Mono.just(new UsernamePasswordAuthenticationToken(
                          user.getUsername(),
                          null,
                          authorities
                      ));
                    });
              });
        });
  }
}
