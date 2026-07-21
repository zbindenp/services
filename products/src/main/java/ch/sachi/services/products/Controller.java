package ch.sachi.services.products;

import ch.sachi.services.products.persistence.Product;
import ch.sachi.services.products.persistence.ProductRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@RestController
public class Controller {
  private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
  private final ProductRepositoryImpl productRepo;
  private static final String HOSTNAME;

  static {
    try {
      HOSTNAME = InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
  }

  public Controller(ProductRepositoryImpl productRepo) {
    this.productRepo = productRepo;
  }

  @GetMapping("/hello")
  public String hello() {
    LOGGER.info("Hello called");
    return "Hello world from " + HOSTNAME;
  }

  @GetMapping("products")
  public List<ProductDto> getAllProducts() {
    LOGGER.info("Start getting products");
    final List<Product> products = productRepo.getAllProducts();
    LOGGER.info("We have {} products", products.size());
    return products.stream()
        .map(p -> new ProductDto(p.getId(), p.getName()))
        .toList();
  }
}
