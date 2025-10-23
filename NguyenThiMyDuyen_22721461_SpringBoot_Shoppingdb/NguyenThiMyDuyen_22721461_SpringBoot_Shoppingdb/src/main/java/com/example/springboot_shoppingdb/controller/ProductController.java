package com.example.springboot_shoppingdb.controller;

import com.example.springboot_shoppingdb.entities.Category;
import com.example.springboot_shoppingdb.entities.Product;
import com.example.springboot_shoppingdb.entities.Comment;
import com.example.springboot_shoppingdb.repositories.CategoryRepository;
import com.example.springboot_shoppingdb.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // InitBinder: trim strings and allow empty numeric values (converted to null)
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
        binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));
        binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
        binder.registerCustomEditor(Float.class, new CustomNumberEditor(Float.class, true));
    }

    // List all products — initialize comments for each product so the view can
    // access them
    @GetMapping
    @Transactional(readOnly = true)
    public String listProducts(Model model,
            @RequestParam(name = "q", required = false) String q) {
        Iterable<Product> productsIt = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        if (productsIt != null) {
            for (Product p : productsIt) {
                // initialize comments collection while persistence context is open
                if (p.getComments() != null) {
                    p.getComments().size(); // touch to force loading
                }
                products.add(p);
            }
        }
        // filter by q if provided (case-insensitive)
        if (q != null && !q.isBlank()) {
            String term = q.trim().toLowerCase();
            products = products.stream()
                    .filter(p -> p.getName() != null && p.getName().toLowerCase().contains(term))
                    .collect(Collectors.toList());
            model.addAttribute("q", q.trim());
        } else {
            model.addAttribute("q", "");
        }
        model.addAttribute("products", products);
        return "product/list";
    }

    // Show create form
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "product/create";
    }

    // Handle create (ensure comments from binding are removed)
    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product,
            @RequestParam(name = "categoryId", required = false) String categoryIdStr) {
        Integer categoryId = null;
        if (categoryIdStr != null && !categoryIdStr.isBlank()) {
            try {
                categoryId = Integer.valueOf(categoryIdStr);
            } catch (NumberFormatException ex) {
                categoryId = null;
            }
        }
        Category category = (categoryId != null) ? categoryRepository.findById(categoryId).orElse(null) : null;
        product.setCategory(category);

        // remove any comments that might be bound from the form — do NOT save comments
        // when creating product
        if (product.getComments() != null && !product.getComments().isEmpty()) {
            product.getComments().clear();
        }

        productRepository.save(product);
        return "redirect:/products";
    }

    // Show update form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        return "product/edit";
    }

    // Handle update (ensure comments from binding are removed)
    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") Integer id,
            @ModelAttribute Product product,
            @RequestParam(name = "categoryId", required = false) String categoryIdStr) {
        Integer categoryId = null;
        if (categoryIdStr != null && !categoryIdStr.isBlank()) {
            try {
                categoryId = Integer.valueOf(categoryIdStr);
            } catch (NumberFormatException ex) {
                categoryId = null;
            }
        }
        Category category = (categoryId != null) ? categoryRepository.findById(categoryId).orElse(null) : null;
        product.setId(id);
        product.setCategory(category);

        // remove any comments bound from the form — comments should not be added during
        // product update
        if (product.getComments() != null && !product.getComments().isEmpty()) {
            product.getComments().clear();
        }

        productRepository.save(product);
        return "redirect:/products";
    }

    // Get product by ID (detail) — now transactional to allow accessing lazy
    // collections
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public String getProductById(@PathVariable("id") Integer id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return "redirect:/products";
        }
        // initialize comments while persistence context is open
        if (product.getComments() != null)
            product.getComments().size();
        model.addAttribute("product", product);
        model.addAttribute("comments", product.getComments());
        return "product/detail";
    }

    // Add comment to product (POST /products/{id}/comments)
    // Only allow adding comment when invoiceId is provided (i.e., customer has an
    // invoice)
    @PostMapping("/{id}/comments")
    public String createComment(@PathVariable("id") Integer id,
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "invoiceId", required = false) String invoiceIdStr) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return "redirect:/products";
        }
        // require invoiceId to be present and numeric to allow adding comment
        if (invoiceIdStr == null || invoiceIdStr.isBlank()) {
            return "redirect:/products/" + id; // no invoice => do not add comment
        }
        try {
            Integer invoiceId = Integer.valueOf(invoiceIdStr);
            // If you have an InvoiceRepository, validate invoice existence here.
            // For now we only check invoiceId is numeric; if necessary add invoice
            // validation.
        } catch (NumberFormatException ex) {
            return "redirect:/products/" + id; // invalid invoice id => do not add
        }

        if (text == null || text.trim().isEmpty()) {
            return "redirect:/products/" + id; // ignore empty comment
        }
        Comment comment = new Comment();
        comment.setText(text.trim());
        // use helper to set both sides
        product.addComment(comment);
        productRepository.save(product);
        return "redirect:/products/" + id;
    }

}
