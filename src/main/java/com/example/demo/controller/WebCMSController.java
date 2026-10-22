package com.example.demo.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/webcms")
public class WebCMSController {
    @GetMapping
    public Map<String, String> getWebCMSData() {
        return Map.of(
                "message", "Welcome to the Web CMS API!",
                "version", "1.0",
                "status", "operational"
        );
    }
    
}
