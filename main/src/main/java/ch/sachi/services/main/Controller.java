package ch.sachi.services.main;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  private final ProductsService productService;

  public Controller(ProductsService productService) {
    this.productService = productService;
  }

  @GetMapping("main")
  public MainResult getAllProducts() {
    final Logger logger = LoggerFactory.getLogger(getClass());
    logger.info("Start getting products");
    final List<ProductDto> products = productService.getAllProducts();
    logger.info("We have {} products", products.size());
    return new MainResult(products);
  }
}
