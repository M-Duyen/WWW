package com.example.springboot_shoppingdb.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private double price;

    @Column(name = "in_stock")
    private int inStock;

    // exclude category from generated toString to avoid recursion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude // exclude comments from toString as well
    private List<Comment> comments = new ArrayList<>();

    // Helper to add a comment
    public void addComment(Comment comment) {
        if (comment == null)
            return;
        comments.add(comment);
        comment.setProduct(this);
    }

    // Helper to remove a comment
    public void removeComment(Comment comment) {
        if (comment == null)
            return;
        comments.remove(comment);
        comment.setProduct(null);
    }
}
