package com.example.springboot_shoppingdb.controller;

import com.example.springboot_shoppingdb.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.Authenticator;
import java.time.LocalDate;
import java.util.Locale;

@Controller
@RequestMapping("/home")
public class HomeController {

    public HomeController() {
        super();
    }

    @GetMapping
    public String HomePage( Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username;
        if (auth != null && auth.isAuthenticated()) {
            username = auth.getName();
        } else {
            username = "Guest";
        }

        LocalDate date = LocalDate.now();
        String mess = "Welcome Thymeleaf " + username ;
        model.addAttribute("message", mess);
        model.addAttribute("date", date.toString());
        return "home";
    }
}
