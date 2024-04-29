package ch.sachi.services.products;

import ch.sachi.services.products.persistence.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> getAllProducts();
}
