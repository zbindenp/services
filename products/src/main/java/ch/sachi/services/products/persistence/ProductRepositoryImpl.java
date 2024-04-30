package ch.sachi.services.products.persistence;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductRepositoryImpl implements ch.sachi.services.products.ProductRepository {
    private final JpaProductRepository jpaRepo;

    public ProductRepositoryImpl(JpaProductRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public List<Product> getAllProducts() {
        return jpaRepo.findAll();
    }
}
