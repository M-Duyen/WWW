package com.example.springboot_shoppingdb.services;

import java.util.List;
import java.util.Optional;

import com.example.springboot_shoppingdb.entities.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot_shoppingdb.repositories.CategoryRepository;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Lấy tất cả category
     */
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * Tìm category theo id, ném RuntimeException nếu không tồn tại
     */
    public Category findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    /**
     * Tìm theo tên (nếu repository có hỗ trợ)
     */
    public Optional<Category> findByName(String name) {
        // assumes repository declares Optional<Category> findByName(String name)
        return categoryRepository.findByName(name);
    }

    /**
     * Tạo mới category
     */
    public Category create(Category category) {
        // ensure id is null so save = insert
        category.setName(category.getName());
        return categoryRepository.save(category);
    }

    /**
     * Cập nhật category theo id. Sao chép thuộc tính từ 'category' sang thực thể
     * hiện có,
     * bỏ qua trường 'id'.
     */
    public Category update(Integer id, Category category) {
        Category existing = findById(id);
        BeanUtils.copyProperties(category, existing, "id");
        return categoryRepository.save(existing);
    }

    /**
     * Xóa category theo id
     */
    public void delete(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
