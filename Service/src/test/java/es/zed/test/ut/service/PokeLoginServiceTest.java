package es.zed.test.ut.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import es.zed.application.service.PokeLoginService;
import es.zed.domain.output.entity.RoleEntity;
import es.zed.domain.output.entity.UserEntity;
import es.zed.domain.output.repository.RolesRepository;
import es.zed.domain.output.repository.UserRepository;
import es.zed.dto.request.LoginRequestDto;
import es.zed.respmodel.ReqRespModel;
import es.zed.security.JwtBearerToken;
import es.zed.security.JwtService;
import es.zed.security.PokeAuthentication;
import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

class PokeLoginServiceTest {

  @InjectMocks
  private PokeLoginService pokeLoginService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private RolesRepository rolesRepository;

  @Mock
  private JwtService jwtService;

  @Mock
  private ReactiveValueOperations<String, String> redisOps;

  private final String username = "testUser";
  private final String password = "testPassword";
  private final String roleId = UUID.randomUUID().toString();
  private final String token = "testJwtToken";

  private LoginRequestDto loginRequestDto;
  private JwtBearerToken jwtBearerToken;
  private UserEntity mockUserEntity;
  private RoleEntity mockRoleEntity;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    PodamFactory podamFactory = new PodamFactoryImpl();

    jwtBearerToken = podamFactory.manufacturePojo(JwtBearerToken.class);
    mockRoleEntity = new RoleEntity();
    mockUserEntity = new UserEntity();
    loginRequestDto = new LoginRequestDto();

    mockUserEntity.setPassword(password);
    mockUserEntity.setUsername(username);
    mockUserEntity.setRoleId(roleId);

    mockRoleEntity.setUsername(username);
    mockRoleEntity.setId(roleId);

    loginRequestDto.setPassword(password);
    loginRequestDto.setUsername(username);
  }

  @Test
  void testLoginSuccess() {
    when(userRepository.findById(username)).thenReturn(Mono.just(mockUserEntity));
    when(rolesRepository.findById(mockUserEntity.getRoleId())).thenReturn(Mono.just(mockRoleEntity));
    when(jwtService.createJwtFromSpec(any(JwtBearerToken.class))).thenReturn(token);
    when(redisOps.set(eq(mockUserEntity.getUsername()), anyString(), any(Duration.class))).thenReturn(Mono.just(true));

    Mono<ResponseEntity<ReqRespModel<String>>> responseMono = pokeLoginService.login(loginRequestDto);
    ResponseEntity<ReqRespModel<String>> response = responseMono.block();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(token, response.getBody().getData());
    assertEquals("Success", response.getBody().getMessage());
  }

  @Test
  void testLoginInvalidPassword() {
    mockUserEntity.setPassword("correctPassword");
    loginRequestDto.setPassword("wrongPassword");

    when(userRepository.findById(username)).thenReturn(Mono.just(mockUserEntity));

    Mono<ResponseEntity<ReqRespModel<String>>> responseMono = pokeLoginService.login(loginRequestDto);
    ResponseEntity<ReqRespModel<String>> response = responseMono.block();

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertEquals("Invalid Credentials", response.getBody().getMessage());
  }


  @Test
  void testLoginInvalidUsername() {
    when(userRepository.findById(username)).thenReturn(Mono.empty());

    Mono<ResponseEntity<ReqRespModel<String>>> responseMono = pokeLoginService.login(loginRequestDto);
    ResponseEntity<ReqRespModel<String>> response = responseMono.block();

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertEquals("Invalid Username", response.getBody().getMessage());
  }

  @Test
  void testLogoutSuccess() {
    PokeAuthentication auth = new PokeAuthentication(jwtBearerToken);
    when(redisOps.delete(anyString())).thenReturn(Mono.just(Boolean.TRUE));

    Mono<ResponseEntity<ReqRespModel<String>>> responseMono = pokeLoginService.logout(auth);
    ResponseEntity<ReqRespModel<String>> response = responseMono.block();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Success", response.getBody().getMessage());
  }

  @Test
  void testLogoutNotFound() {
    PokeAuthentication auth = new PokeAuthentication(jwtBearerToken);
    when(redisOps.delete(anyString())).thenReturn(Mono.just(Boolean.FALSE));

    Mono<ResponseEntity<ReqRespModel<String>>> responseMono = pokeLoginService.logout(auth);
    ResponseEntity<ReqRespModel<String>> response = responseMono.block();

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Token not found or already invalidated", response.getBody().getMessage());
  }
}