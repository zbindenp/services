package ch.sachi.services.categories;

import ch.sachi.services.categories.persistence.Category;
import ch.sachi.services.categories.persistence.CategoryRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@RestController
public class Controller {
  private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
  private final CategoryRepositoryImpl categoryRepo;
  private static final String HOSTNAME;

  static {
    try {
      HOSTNAME = InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
  }

  public Controller(CategoryRepositoryImpl categoryRepository) {
    this.categoryRepo = categoryRepository;
  }

  @GetMapping("/hello")
  public String hello() {
    LOGGER.info("Hello called");
    return "Hello world from " + HOSTNAME;
  }

  @GetMapping("categories")
  public List<CategoryDto> getAllCategories() {
    LOGGER.info("Start getting categories");
    final List<Category> categories = categoryRepo.getAllCategories();
    LOGGER.info("We have {} categories", categories.size());
    return categories.stream()
        .map(p -> new CategoryDto(p.getId(), p.getName()))
        .toList();
  }

  @GetMapping("/insert/{count}")
  public String aaa(@PathVariable("count") long count) {
    LOGGER.info("Insert called for [{}]categories, I know, this is not good practice", count);
    long start = System.currentTimeMillis();
    categoryRepo.insert(count);
    long duration = System.currentTimeMillis() - start;
    LOGGER.info("Insert had [{}]ms", duration);
    return "Inserted ";
  }
}
