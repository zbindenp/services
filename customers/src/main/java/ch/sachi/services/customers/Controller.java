package ch.sachi.services.customers;

import ch.sachi.services.customers.persistence.Customer;
import ch.sachi.services.customers.persistence.CustomerRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@RestController
public class Controller {
  private final CustomerRepositoryImpl customerRepo;

  public Controller(CustomerRepositoryImpl customerRepo) {
    this.customerRepo = customerRepo;
  }

  @GetMapping("customers")
  public List<CustomerDto> getAllCustomers() throws InterruptedException {
    final Logger logger = LoggerFactory.getLogger(getClass());
    logger.info("Start getting customers");
    final List<Customer> customers = customerRepo.getAllCustomers();
    logger.info("We have {} customers", customers.size());
    return customers.stream()
            .map(p -> new CustomerDto(p.getId(), p.getName()))
            .toList();
  }

  @GetMapping("load")
  public ResponseEntity<String> load() throws UnknownHostException {
    final Logger logger = LoggerFactory.getLogger(getClass());
    logger.info("Load called");
    return ResponseEntity.ok()
            .body(InetAddress.getLocalHost().getHostName());
  }
}
