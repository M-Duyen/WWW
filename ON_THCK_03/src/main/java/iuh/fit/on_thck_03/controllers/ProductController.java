package iuh.fit.on_thck_03.controllers;

import iuh.fit.on_thck_03.models.Product;
import iuh.fit.on_thck_03.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }
    @GetMapping()
    public String getAllProducts(Model model){
        List<Product> products =   repository.findAll();
        model.addAttribute("products", products);
        return "product";
    }

    @GetMapping("/{id}")
    public String getProductById(Model model, @PathVariable("id") int id){
        Product product = repository.findById(id).orElseThrow();
        model.addAttribute("product", product);
        return "detail";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("product", new Product());
        return "create";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product){
        repository.save(product);
        return "redirect:/product";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(Model model, @PathVariable("id") int id){
        Product product = repository.findById(id).orElseThrow();
        model.addAttribute("product", product);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@ModelAttribute Product product, @PathVariable("id") int id){
        product.setId(id);
        repository.save(product);
        return "redirect:/product";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        repository.deleteById(id);
        return "redirect:/product";
    }

}
