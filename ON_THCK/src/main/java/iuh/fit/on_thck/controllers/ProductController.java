package iuh.fit.on_thck.controllers;

import iuh.fit.on_thck.models.Product;
import iuh.fit.on_thck.services.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String listProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "detail";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String showCreateProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/products";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(Model model, @PathVariable("id") int id) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "editForm";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") int id, @ModelAttribute Product product) {
        product.setId(id);
        productService.save(product);
        return "redirect:/products";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.delete(id);
        return "redirect:/products";
    }


}
