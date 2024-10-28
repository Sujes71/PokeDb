package es.zed.shared.utils;

/**
 * Constants.
 */
public class Constants {

  /**
   * base path url.
   */
  public static final String BASE_URL = "/api";

  /**
   * pokemon url.
   */
  public static final String POKE_DB_POKEMON = "/pokemon";

  /**
   * pokemon url.
   */
  public static final String POKE_DB_ABILITY = "/ability";

  /**
   * login.
   */
  public static final String POKE_DB_LOGIN = "/login";

  /**
   * logout.
   */
  public static final String POKE_DB_LOGOUT = "/logout";

  /**
   * pokemon url.
   */
  public static final String POKE_DB_ABILITY_NID = POKE_DB_ABILITY +  "/{nid}";

  /**
   * pokemon url.
   */
  public static final String POKE_DB_POKEMON_NID = POKE_DB_POKEMON +  "/{nid}";

  /**
   * nid.
   */
  public static final String NID_URL_FILTER = "{nid}";

  /**
   * Api authorities.
   */
  public static final String API_AUTHORITIES = "hasAuthority('ADMIN')";

  /**
   * Kafka group id.
   */
  public static final String KAFKA_GROUP_ID = "PokeDb";

  /**
   * Poke updated event.
   */
  public static final String POKE_UPDATED_EVENT = "event.PokeUpdatedEvent";

  /**
   * Kafka.
   */
  public static final String KAFKA_MANAGER = "KAFKA";

  /**
   * All db pokemon cache.
   */
  public static final String ALL_DB_POKEMON_CACHE = "'AllDbPokemon'";

  /**
   * Poke cache.
   */
  public static final String POKE_CACHE = "PokeCache";

  /**
   * AB nid caches.
   */
  public static final String AB_NID_CACHE = "'Ab' + #nid";
}
