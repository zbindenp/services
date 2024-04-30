package ch.sachi.services.main;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  private final ProductsService productService;
  private final CustomerService customerService;

  public Controller(ProductsService productService, CustomerService customerService) {
    this.productService = productService;
    this.customerService = customerService;
  }

  @GetMapping("main")
  public MainResult getAllProducts() {
    final Logger logger = LoggerFactory.getLogger(getClass());
    logger.info("Start getting products");
    final List<ProductInfoDto> products = productService.getAllProducts();
    logger.info("We have {} products", products.size());
    final List<CustomerInfo> customers = customerService.getAllCustomers();
    return new MainResult(products, customers);
  }
}
