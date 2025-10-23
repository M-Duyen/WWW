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

    @PostMapping("/register")
    public String doRegister(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam(name = "age", required = false) Integer age,
            @RequestParam("password") String password,
            @RequestParam(name = "confirmPassword", required = false) String confirmPassword,
            RedirectAttributes ra) {

        // basic validation
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            ra.addAttribute("error", "Email and password are required");
            return "redirect:/register";
        }
        if (confirmPassword != null && !confirmPassword.equals(password)) {
            ra.addAttribute("error", "Passwords do not match");
            return "redirect:/register";
        }

        // kiểm tra email đã tồn tại (trong customer table)
        if (customerRepository.findByEmail(email).isPresent()) {
            ra.addAttribute("error", "Email already registered");
            return "redirect:/register";
        }

        // tạo Customer (Customer extends User — sẽ persist cả users và customers do inheritance)
        Customer c = new Customer();
        c.setName(name != null ? name.trim() : "");
        c.setEmail(email.trim());
        c.setAge(age != null ? age : 0);
        c.setRole("customer"); // lưu role dưới dạng "customer" — CustomUserDetailsService sẽ map thành ROLE_CUSTOMER
        c.setPassword(passwordEncoder.encode(password));

        customerRepository.save(c);

        return "redirect:/login?success";
    }
}
