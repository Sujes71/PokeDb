package es.zed.domain.output.object;

import es.zed.domain.output.entity.PokemonEntity;
import lombok.Data;

/**
 * Pokemon object.
 */
@Data
public class PokemonObject {

  /**
   * id.
   */
  private String id;

  /**
   * name.
   */
  private String name;

  /**
   * From entity to object.
   *
   * @param pokemonEntity entity.
   * @return pokemonObject.
   */
  public static PokemonObject fromEntityToObject(PokemonEntity pokemonEntity) {
    PokemonObject pokemon = new PokemonObject();
    pokemon.id = pokemonEntity.getNativeId();
    pokemon.name = pokemonEntity.getName();
    return pokemon;
  }
}
