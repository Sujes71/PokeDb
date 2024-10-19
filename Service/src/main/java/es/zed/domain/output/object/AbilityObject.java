package es.zed.domain.output.object;

import es.zed.domain.output.entity.AbilityEntity;
import lombok.Builder;
import lombok.Data;

/**
 * Ability object.
 */
@Data
@Builder
public class AbilityObject {

  /**
   * id.
   */
  private String name;

  /**
   * name.
   */
  private String url;

  /**
   * From entity to object.
   *
   * @param abilityEntity entity.
   * @return pokemonObject.
   */
  public static AbilityObject fromEntityToObject(AbilityEntity abilityEntity) {
    return new AbilityObject(abilityEntity.getName(), abilityEntity.getUrl());
  }
}
