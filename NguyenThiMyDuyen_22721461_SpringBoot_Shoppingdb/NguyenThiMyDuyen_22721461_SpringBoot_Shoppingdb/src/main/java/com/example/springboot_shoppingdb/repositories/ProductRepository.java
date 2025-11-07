package com.example.springboot_shoppingdb.repositories;

import com.example.springboot_shoppingdb.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String chatbot);
    List<Product> findByNameContainingIgnoreCase(String name);
}
