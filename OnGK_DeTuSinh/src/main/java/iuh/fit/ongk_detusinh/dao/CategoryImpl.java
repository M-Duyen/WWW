package iuh.fit.ongk_detusinh.dao;

import iuh.fit.ongk_detusinh.model.Category;

import java.util.List;

public interface CategoryImpl {
    List<Category> getAllCategory();

    Category getCategoryById(String id);

    void addCategory(Category category);

    void updateCategory(Category category);

    boolean deleteCategory(String id);

    List<Category> getCategoryByName(String categoryName);
}
