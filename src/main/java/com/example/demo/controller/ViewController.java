package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // 👈 Note: Use @Controller, NOT @RestController
public class ViewController {

    @GetMapping("/") // Maps the root domain route
    public String index() {
        return "index"; // Looks for 'index.html' in src/main/resources/templates/
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Looks for 'login.html'
    }
    @PostMapping("/login")
    public String login() {
        System.out.println("Login POST request received"); // Debugging line
        // Spring Security will handle the login process, so this method can be empty
        return "redirect:/"; // Redirect to home page af
    }
}