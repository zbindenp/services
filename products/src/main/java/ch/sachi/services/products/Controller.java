package ch.sachi.services.products;

import ch.sachi.services.products.persistence.Product;
import ch.sachi.services.products.persistence.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    private final ProductRepository productRepo;

    public Controller(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello world";
    }

    @GetMapping("products")
    public List<ProductDto> getAllProducts() {
        final Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Start getting products");
        final List<Product> products = productRepo.getAllProducts();
        logger.info("We have {} products", products.size());
        return products.stream()
                .map(p -> new ProductDto(p.getId(), p.getName()))
                .toList();
    }
}
