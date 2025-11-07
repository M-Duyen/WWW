package iuh.fit.on_thck.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/home")
public class HomeController {
    public HomeController() {
    }
    @GetMapping()
    public String homePage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username;
        if(auth != null && auth.isAuthenticated()){
            username = auth.getName();
        }else{
            username = "Guest";
        }
        LocalDate localDate = LocalDate.now();
        String mess = "Welcome Thymeleaf " + username;
        model.addAttribute("mess", mess);
        model.addAttribute("localDate", localDate);
        return "home";
    }
}
