package es.zed.domain.output.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * user roles.
 */
@Table("user_roles")
@NoArgsConstructor
@Data
public class UserRoleEntity {

  /**
   * id.
   */
  @Id
  private Long id;

  /**
   * username.
   */
  private String username;

  /**
   * role id.
   */
  private String roleId;

}

