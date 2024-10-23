package es.zed.infrastructure.api.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import es.zed.abstracts.AbstractEnpoint;
import es.zed.domain.output.api.PokeDbOutputPort;
import es.zed.dto.response.AbilityResponseDto;
import es.zed.utils.CustomObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * PokeDb endpoint.
 */
@Component
public class PokeDbEndpoint extends AbstractEnpoint implements PokeDbOutputPort {

  /**
   * Constructor.
   *
   * @param restTemplate rest template.
   * @param customObjectMapper mapper.
   */
  public PokeDbEndpoint(final RestTemplate restTemplate, final CustomObjectMapper customObjectMapper) {
    super(restTemplate, customObjectMapper);
  }

  /**
   * Do call get internal pokemon.
   *
   * @param url url.
   * @param auth auth.
   * @return response.
   */
  @Override
  public AbilityResponseDto doCallGetInternalPokemon(final String url, final String auth) {
    return doCallInternal(url, HttpMethod.GET, addDefaultHeaders(auth), null, new TypeReference<>() {});
  }
}
