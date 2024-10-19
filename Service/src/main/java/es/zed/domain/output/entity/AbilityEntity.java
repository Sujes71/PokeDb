package es.zed.domain.output.entity;

import es.zed.domain.output.object.AbilityObject;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Ability entity.
 */
@NoArgsConstructor
@Table("Ability")
public class AbilityEntity implements Persistable<UUID> {

  /**
   * uuid.
   */
  @Id
  private UUID uuid;

  /**
   * name.
   */
  @Getter
  private String name;

  /**
   * url.
   */
  @Getter
  private String url;

  /**
   * Get uuid.
   *
   * @return uuid.
   */
  @Override
  public UUID getId() {
    return this.uuid;
  }

  /**
   * isNew.
   *
   * @return boolean.
   */
  @Override
  public boolean isNew() {
    if (Objects.isNull(this.uuid)) {
      this.uuid = UUID.randomUUID();
      return true;
    }
    return false;
  }

  /**
   * From object to entity.
   *
   * @param abilityObject object.
   * @return abilityEntity.
   */
  public static AbilityEntity fromObjectToEntity(AbilityObject abilityObject) {
    AbilityEntity ability = new AbilityEntity();
    ability.name = abilityObject.getName();
    ability.url = abilityObject.getUrl();
    return ability;
  }
}