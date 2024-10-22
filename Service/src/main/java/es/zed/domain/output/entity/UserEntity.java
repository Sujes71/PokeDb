package es.zed.domain.output.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * User entity.
 */
@Table("users")
@NoArgsConstructor
@Data
public class UserEntity {

  /**
   * username.
   */
  @Id
  private String username;

  /**
   * password.
   */
  private String password;

  /**
   * Role id.
   */
  private String roleId;

}
