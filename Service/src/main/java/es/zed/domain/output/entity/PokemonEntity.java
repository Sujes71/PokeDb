package es.zed.domain.output.entity;

import es.zed.domain.output.object.PokemonObject;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Pokemon entity.
 */
@NoArgsConstructor
@Table("Pokemon")
public class PokemonEntity implements Persistable<UUID> {

  /**
   * uuid.
   */
  @Id
  private UUID uuid;

  /**
   * id.
   */
  @Column("id")
  private String id;

  /**
   * name.
   */
  @Getter
  @Column("name")
  private String name;

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
   * Get native id.
   *
   * @return id.
   */
  public String getNativeId() {
    return this.id;
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
   * @param pokemonObject object.
   * @return pokemonEntity.
   */
  public static PokemonEntity fromObjectToEntity(PokemonObject pokemonObject) {
    PokemonEntity pokemon = new PokemonEntity();
    pokemon.id = pokemonObject.getId();
    pokemon.name = pokemonObject.getName();
    return pokemon;
  }
}