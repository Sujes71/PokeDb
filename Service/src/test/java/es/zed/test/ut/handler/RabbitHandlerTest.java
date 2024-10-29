package es.zed.test.ut.handler;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.zed.application.handler.PokeDbRabbitEventHandler;
import es.zed.domain.output.object.PokemonObject;
import es.zed.infrastructure.adapter.PokemonRepositoryAdapter;
import es.zed.pokeapi.PokeCreatedEvent;
import es.zed.pokeapi.PokeCreatedEventBody;
import es.zed.utils.CustomObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

class RabbitHandlerTest {

  @Mock
  private PokemonRepositoryAdapter pokemonRepositoryAdapter;

  @Mock
  private CustomObjectMapper mapper;

  @InjectMocks
  private PokeDbRabbitEventHandler pokeDbRabbitEventHandler;

  private PokeCreatedEvent pokeCreatedEvent;
  private PokemonObject pokemonObject;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    PodamFactory podamFactory = new PodamFactoryImpl();

    pokeCreatedEvent = podamFactory.manufacturePojo(PokeCreatedEvent.class);
    PokeCreatedEventBody body = new PokeCreatedEventBody();
    body.setId("123");
    body.setName("Pikachu");
    pokeCreatedEvent.setBody(body);

    pokemonObject = new PokemonObject();
    pokemonObject.setId("123");
    pokemonObject.setName("Pikachu");
  }

  @Test
  void testHandlePokeUpdatedEvent() {
    when(mapper.convertValue(pokeCreatedEvent.getBody(), PokemonObject.class)).thenReturn(pokemonObject);
    pokeDbRabbitEventHandler.handlePokeCreatedEvent(pokeCreatedEvent);

    verify(pokemonRepositoryAdapter, times(1)).save(pokemonObject);
  }

}
