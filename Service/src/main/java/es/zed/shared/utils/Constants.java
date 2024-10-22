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
   * pokemon url.
   */
  public static final String POKE_DB_LOGIN = "/login";

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
   * nid.
   */
  public static final String STATUS_HEADER = "x-status";
}
