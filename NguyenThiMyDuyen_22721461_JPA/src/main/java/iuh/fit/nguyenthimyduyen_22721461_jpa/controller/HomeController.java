package iuh.fit.nguyenthimyduyen_22721461_jpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String homePage(Model model){
        LocalDate date = LocalDate.now();
        String message = "Welcome to the Department and Employee Management System! Today is " + date;
        model.addAttribute("message", message);
        model.addAttribute("date", date.toString());
        return "home";
    }
}
