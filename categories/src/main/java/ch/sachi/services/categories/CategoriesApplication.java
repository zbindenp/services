package ch.sachi.services.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CategoriesApplication {
  private static final Logger LOGGER = LoggerFactory.getLogger(CategoriesApplication.class);

  public static void main(String[] args) {
    LOGGER.info("Starting Application");
    SpringApplication.run(CategoriesApplication.class, args);
  }

}
