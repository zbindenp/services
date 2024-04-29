package ch.sachi.services.products;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public class ProductDto {
    private final Long id;
    private final String name;

    public ProductDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
