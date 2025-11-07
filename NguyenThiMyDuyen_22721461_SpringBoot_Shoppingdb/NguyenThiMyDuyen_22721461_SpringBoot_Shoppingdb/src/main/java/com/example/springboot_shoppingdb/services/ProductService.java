package com.example.springboot_shoppingdb.services;

import com.example.springboot_shoppingdb.entities.Product;
import com.example.springboot_shoppingdb.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(int id, Product product) {
        product.setId(id);
        return productRepository.save(product);
    }
    public List<Product> findByNameContainingIgnoreCase(String name){
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
