package es.zed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * move-money-service main Application.
 */
@EnableR2dbcRepositories
@SpringBootApplication
public class Application {

  /**
   * Microservice startup entrypoint.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}