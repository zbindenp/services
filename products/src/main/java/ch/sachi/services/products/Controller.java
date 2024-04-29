package ch.sachi.services.products;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class Controller {
    @GetMapping("/hello")
    public String hello() {
        return "Hello world";
    }

    @GetMapping("products")
    public List<ProductDto> getAllProducts() {
        return List.of(new ProductDto(1L, "Banane"));
    }
}
