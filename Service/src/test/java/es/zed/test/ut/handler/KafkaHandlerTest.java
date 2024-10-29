package es.zed.test.ut.handler;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.zed.application.handler.PokeDbKafkaEventHandler;
import es.zed.domain.output.object.PokemonObject;
import es.zed.infrastructure.adapter.PokemonRepositoryAdapter;
import es.zed.pokeapi.PokeUpdatedEvent;
import es.zed.pokeapi.PokeUpdatedEventBody;
import es.zed.utils.CustomObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

class KafkaHandlerTest {

  @Mock
  private PokemonRepositoryAdapter pokemonRepositoryAdapter;

  @Mock
  private CustomObjectMapper mapper;

  @InjectMocks
  private PokeDbKafkaEventHandler pokeDbKafkaEventHandler;

  private PokeUpdatedEvent pokeUpdatedEvent;
  private PokemonObject pokemonObject;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    PodamFactory podamFactory = new PodamFactoryImpl();

    pokeUpdatedEvent = podamFactory.manufacturePojo(PokeUpdatedEvent.class);
    PokeUpdatedEventBody body = new PokeUpdatedEventBody();
    body.setId("123");
    body.setName("Pikachu");
    pokeUpdatedEvent.setBody(body);

    pokemonObject = new PokemonObject();
    pokemonObject.setId("123");
    pokemonObject.setName("Pikachu");
  }

  @Test
  void testHandlePokeUpdatedEvent() {
    when(mapper.convertValue(pokeUpdatedEvent.getBody(), PokemonObject.class)).thenReturn(pokemonObject);
    pokeDbKafkaEventHandler.handlePokeUpdatedEvent(pokeUpdatedEvent);

    verify(pokemonRepositoryAdapter, times(1)).update(pokemonObject);
  }
}
