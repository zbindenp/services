package ch.sachi.services.products;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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
        final List<Product> products = productRepo.getAllProducts();
        return products.stream()
                .map(p-> new ProductDto(p.getId(), p.getName()))
                .toList();
    }
}
