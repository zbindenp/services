package ch.sachi.services.main;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {
  private final RestTemplate restTemplate;

  public CustomerService(RestTemplateBuilder builder, @Value("${main.customersbaseurl}") String customersbaseurl) {
    this.restTemplate = builder.rootUri(customersbaseurl).build();
  }

  public List<CustomerInfo> getAllCustomers() {
    final List<CustomerInfo> customers = Arrays.asList(restTemplate.getForObject("/customers", CustomerInfo[].class));
    return Collections.unmodifiableList(customers);
  }
}
