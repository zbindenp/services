package ch.sachi.services.categories;

import ch.sachi.services.categories.persistence.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> getAllCategories();

    void insert(long count);
}
