package com.example.springboot_shoppingdb.repositories;

import com.example.springboot_shoppingdb.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);
}

