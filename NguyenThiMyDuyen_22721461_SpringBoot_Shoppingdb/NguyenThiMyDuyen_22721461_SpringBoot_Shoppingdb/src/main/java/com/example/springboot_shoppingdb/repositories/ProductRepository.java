package com.example.springboot_shoppingdb.repositories;

import com.example.springboot_shoppingdb.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
