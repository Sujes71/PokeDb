package es.zed.test.ut.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.zed.application.service.PokemonDbService;
import es.zed.domain.output.api.PokeDbOutputPort;
import es.zed.domain.output.entity.PokemonEntity;
import es.zed.domain.output.object.PokemonObject;
import es.zed.dto.PokemonDto;
import es.zed.dto.response.AbilityResponseDto;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.infrastructure.adapter.PokemonRepositoryAdapter;
import es.zed.security.JwtBearerToken;
import es.zed.security.JwtService;
import es.zed.security.PokeAuthentication;
import es.zed.utils.CustomObjectMapper;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

class PokemonDbServiceTest {

  @InjectMocks
  private PokemonDbService pokemonDbService;

  @Mock
  private PokemonRepositoryAdapter pokemonRepositoryAdapter;

  @Mock
  private CustomObjectMapper mapper;

  @Mock
  private PokeDbOutputPort pokeDbOutputPort;

  @Mock
  private JwtService jwtService;

  private final String NID = "123";
  private final String CALL_URL = "http://localhost:8080/api/pokemon/{nid}";
  private final String EXPECTED_URL = "http://localhost:8080/api/pokemon/" + NID;
  private final String token = "testJwtToken";

  private PokemonObject mockPokemonObject;
  private PokemonDto mockPokemonDto;
  private List<PokemonEntity> mockPokemonEntities;
  private AbilityResponseDto expectedResponse;
  private JwtBearerToken jwtBearerToken;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(pokemonDbService, "basePath", "http://localhost:8080/api");

    PodamFactory podamFactory = new PodamFactoryImpl();

    mockPokemonDto = podamFactory.manufacturePojo(PokemonDto.class);
    mockPokemonObject = podamFactory.manufacturePojo(PokemonObject.class);
    expectedResponse = podamFactory.manufacturePojo(AbilityResponseDto.class);
    mockPokemonEntities = List.of(PokemonEntity.fromObjectToEntity(mockPokemonObject));
    jwtBearerToken = podamFactory.manufacturePojo(JwtBearerToken.class);
  }

  @Test
  void testGetPokemon() {
    when(pokemonRepositoryAdapter.findAll()).thenReturn(Flux.fromIterable(mockPokemonEntities));
    when(mapper.convertValue(any(), eq(PokemonDto.class))).thenReturn(mockPokemonDto);

    Mono<PokemonResponseDto> result = pokemonDbService.getPokemon();

    PokemonResponseDto pokemonResponseDto = result.block();
    assertEquals(1, pokemonResponseDto.getItems().size());
    verify(pokemonRepositoryAdapter, times(1)).findAll();
  }

  @Test
  void testGetAbility() {
    PokeAuthentication auth = new PokeAuthentication(jwtBearerToken);
    Map<String, String> replacements = Map.of("{nid}", NID);

    when(mapper.mapUrl(replacements, CALL_URL)).thenReturn(EXPECTED_URL);
    when(jwtService.createJwtFromSpec(auth.getJwtBearerToken())).thenReturn(token);
    when(pokeDbOutputPort.doCallGetInternalPokemon(EXPECTED_URL, token)).thenReturn(expectedResponse);

    AbilityResponseDto result = pokemonDbService.getAbility(NID, auth);

    assertEquals(expectedResponse, result);
    verify(pokeDbOutputPort, times(1)).doCallGetInternalPokemon(EXPECTED_URL, token);
  }
}
