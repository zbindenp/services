package ch.sachi.services.main;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductsService {
    private static final Logger log = LoggerFactory.getLogger(ProductsService.class);
    private final RestTemplate restTemplate;

    public ProductsService(RestTemplateBuilder builder, @Value("${main.productsbaseurl}") String productsbaseurl) {
        this.restTemplate = builder
                .rootUri(productsbaseurl)
                .readTimeout(Duration.ofSeconds(10))
                .build();
    }

    public List<ProductInfoDto> getAllProducts() throws DependenciesException {
        try {
            ProductInfoDto[] prds = restTemplate.getForObject("/products", ProductInfoDto[].class);
            final List<ProductInfoDto> products = Arrays.asList(prds);
            return Collections.unmodifiableList(products);
        } catch (RuntimeException re) {
            throw new DependenciesException("Problem in getting products", re);
        }
    }
}
