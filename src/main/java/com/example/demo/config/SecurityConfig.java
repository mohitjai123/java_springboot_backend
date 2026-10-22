package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                "/",
                                "/login",
                                "/v3/api-docs",
                                "/api/webcms/**",
                                "/api/users/**",
                                "/proxy/**",
                                "/api/users",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html")
                        .permitAll() // Explicitly open all OpenAPI/Swagger routes
                        .anyRequest().authenticated())
               .formLogin((form) -> form
                    .loginPage("/login")               // 1. Where to route users to log in
                    .loginProcessingUrl("/login")      // 2. The hidden POST endpoint Spring listens to
                    .defaultSuccessUrl("/", true)      // 3. Where to send users after a successful login
                    .failureUrl("/login?error=true")   // 4. Where to send users if authentication fails
                    .permitAll())
                .logout((logout) -> logout.permitAll());
        // 1. 🔴 CRITICAL: Disable CSRF so Swagger background POST/PUT requests aren't blocked
        http.csrf(csrf -> csrf.disable());

        // 2. 🔴 CRITICAL: Permit Swagger UI's internal iframe rendering
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        return http.build();
    }

    // Creating a temporary test user for authentication
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("admin")
                .password("{noop}password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}