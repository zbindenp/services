package ch.sachi.services.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

  private final ProductsService productService;
  private final CustomerService customerService;

  public Controller(ProductsService productService, CustomerService customerService) {
    this.productService = productService;
    this.customerService = customerService;
  }

  @GetMapping("main")
  public ResponseEntity<MainResult> getAll() {
    final Logger logger = LoggerFactory.getLogger(getClass());
    try {
      logger.info("Start getting products");
      final List<ProductInfoDto> products = productService.getAllProducts();
      logger.info("We have {} products", products.size());
      logger.info("Start getting customers");
      final List<CustomerInfo> customers = customerService.getAllCustomers();
      logger.info("We have {} customers", customers.size());
      return ResponseEntity.ok(new MainResult(products, customers));
    } catch (DependenciesException de) {
      logger.error("Problem in getting data", de);
      return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
  }
}
