package es.zed.infrastructure.api.endpoint;

import es.zed.abstracts.AbstractEnpoint;
import es.zed.domain.output.api.PokeDbOutputPort;
import es.zed.dto.response.AbilityResponseDto;
import org.springframework.http.HttpHeaders;
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
   */
  public PokeDbEndpoint(RestTemplate restTemplate) {
    super(restTemplate);
  }

  /**
   * Do call get pokemon.
   *
   * @param url url.
   * @return response.
   */
  @Override
  public AbilityResponseDto doCallGetPokemon(final String url, final String auth) {
    return doCall(url, HttpMethod.GET, addDefaultHeaders(auth), null, AbilityResponseDto.class);
  }

}
