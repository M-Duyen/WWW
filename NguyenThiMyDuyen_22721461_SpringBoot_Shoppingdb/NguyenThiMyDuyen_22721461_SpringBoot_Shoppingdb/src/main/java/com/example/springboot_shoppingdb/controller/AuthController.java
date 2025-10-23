package com.example.springboot_shoppingdb.controller;

import com.example.springboot_shoppingdb.entities.Customer;
import com.example.springboot_shoppingdb.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    @Autowired
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AuthController(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginForm(Authentication auth) {
        // nếu đã đăng nhập chuyển hướng về home
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/home";
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "auth/register";
    }

    // handle registration
    @PostMapping("/register")
    public String registerUser(@RequestParam("name") String name,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam("password") String password,
            RedirectAttributes ra) {

        if (name == null || name.isBlank() || password == null || password.isBlank()) {
            ra.addFlashAttribute("error", "Name and password are required");
            return "redirect:/register";
        }

        if (customerRepository.findByName(name.trim()).isPresent()
                || (email != null && !email.isBlank() && customerRepository.findByEmail(email.trim()).isPresent())) {
            ra.addFlashAttribute("error", "User with same name or email already exists");
            return "redirect:/register";
        }

        Customer c = new Customer();
        c.setName(name.trim());
        if (email != null && !email.isBlank())
            c.setEmail(email.trim());
        c.setPassword(passwordEncoder.encode(password));
        customerRepository.save(c);

        ra.addFlashAttribute("message", "Registration successful; please log in");
        return "redirect:/login";
    }
}
