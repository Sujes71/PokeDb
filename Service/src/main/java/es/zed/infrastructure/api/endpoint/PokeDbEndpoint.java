package es.zed.infrastructure.api.endpoint;

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
   * Do call get pokemon.
   *
   * @param url url.
   * @return response.
   */
  @Override
  public AbilityResponseDto doCallGetInternalPokemon(final String url, final String auth) {
    return doCallInternal(url, HttpMethod.GET, addDefaultHeaders(auth), null, AbilityResponseDto.class);
  }
}
