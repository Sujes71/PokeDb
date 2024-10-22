package es.zed.domain.output.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * user roles.
 */
@Table("roles")
@NoArgsConstructor
@Data
public class RoleEntity {

  /**
   * id.
   */
  @Id
  private String id;

  /**
   * username.
   */
  private String username;

}

