package ch.sachi.services.products.persistence;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductRepository implements ch.sachi.services.products.ProductRepository {
    private final JpaProductRepository jpaRepo;

    public ProductRepository(JpaProductRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public List<Product> getAllProducts() {
        return jpaRepo.findAll();
    }
}
