package com.example.springboot_shoppingdb.controller;

import com.example.springboot_shoppingdb.entities.Product;
import com.example.springboot_shoppingdb.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductSearchController {

    private final ProductService productService;

    public ProductSearchController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/products/search?q=...
    @GetMapping("/api/products/search")
    public List<Product> search(@RequestParam("q") String q) {
        return productService.findByNameContainingIgnoreCase(q);
    }
}
