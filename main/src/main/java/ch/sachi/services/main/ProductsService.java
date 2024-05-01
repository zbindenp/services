package ch.sachi.services.main;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductsService {
  private final RestTemplate restTemplate;

  public ProductsService(RestTemplateBuilder builder, @Value("${main.productsbaseurl}") String productsbaseurl) {
    this.restTemplate = builder.rootUri(productsbaseurl).build();
  }

  public List<ProductInfoDto> getAllProducts() {
    final List<ProductInfoDto> products = Arrays.asList(restTemplate.getForObject("/products", ProductInfoDto[].class));
    return Collections.unmodifiableList(products);
  }
}
