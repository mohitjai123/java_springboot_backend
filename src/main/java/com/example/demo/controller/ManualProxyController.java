package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/proxy")
public class ManualProxyController {

    private final RestTemplate restTemplate = new RestTemplate();
     @Value("${weather.api.url}")
    private String baseUrl;

    @GetMapping("/weather")
    public ResponseEntity<String> proxyWeatherRequest(HttpServletRequest request) {
        // Define the external target destination
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/todos", 
                HttpMethod.GET, 
                entity, 
                String.class
        );

        // Return the JSON data directly back to your frontend client
        return ResponseEntity
                .status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.getBody());
    }
}