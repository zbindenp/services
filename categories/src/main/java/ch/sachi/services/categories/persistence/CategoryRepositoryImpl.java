package ch.sachi.services.categories.persistence;

import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CategoryRepositoryImpl implements ch.sachi.services.categories.CategoryRepository {
    private final JpaCategoryRepository jpaRepo;

    public CategoryRepositoryImpl(JpaCategoryRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public List<Category> getAllCategories() {
        return jpaRepo.findAll();
    }

    @Override
    public void insert(long count) {
        Collection<Category> categories = new ArrayList<>();
        for (long i = 1; i <= count; i++) {
            Category category = new Category();
            category.setName("name" + i);
            categories.add(category);
        }
        jpaRepo.saveAll(categories);
    }
}
