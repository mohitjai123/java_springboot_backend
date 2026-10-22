package com.example.demo.model;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "users") // Replaces JPA @Entity and @Table
public class User {

    @Id // Tells Spring this is the MongoDB primary unique key (_id)
    private String id;

    @NotBlank(message = "Name is required and cannot be blank") // 👈 Prevents null or ""
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")

    @Indexed(unique = true)
    private String email;
    private String profileImage;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public User() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public LocalDateTime getUpdatedAt(){ return updatedAt;}
    public LocalDateTime getCreatedAt(){ return createdAt;}
    public String getProfileImage(){ 
         if (this.profileImage == null || this.profileImage.isEmpty()) {
            return null;
        }
        
        // Dynamically grabs "http://localhost:8080" (or your production domain) at runtime
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(this.profileImage)
                .toUriString();
    }
    public void setProfileImage(String imString){this.profileImage = imString;}
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}