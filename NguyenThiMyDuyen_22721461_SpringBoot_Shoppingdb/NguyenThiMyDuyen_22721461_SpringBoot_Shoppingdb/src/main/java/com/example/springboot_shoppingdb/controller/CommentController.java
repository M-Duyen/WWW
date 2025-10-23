package com.example.springboot_shoppingdb.controller;

import com.example.springboot_shoppingdb.entities.Comment;
import com.example.springboot_shoppingdb.entities.Product;
import com.example.springboot_shoppingdb.repositories.CommentRepository;
import com.example.springboot_shoppingdb.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/products/{productId}/comments")
public class CommentController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CommentRepository commentRepository;

    // Show form to add comment (optional, useful for Thymeleaf)
    @GetMapping("/create")
    public String showCreateForm(@PathVariable("productId") Integer productId, Model model) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null)
            return "redirect:/products";
        model.addAttribute("product", product);
        model.addAttribute("comment", new Comment());
        return "comment/create"; // tạo template comment/create.html nếu cần
    }

    // Handle create comment
    @PostMapping("/create")
    public String createComment(@PathVariable("productId") Integer productId,
            @RequestParam("text") String text) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return "redirect:/products";
        }
        Comment comment = new Comment();
        comment.setText(text);
        // Gắn product vào comment và lưu
        comment.setProduct(product);
        commentRepository.save(comment);

        // hoặc dùng product.addComment(comment) và productRepository.save(product);
        return "redirect:/products/" + productId;
    }
}
