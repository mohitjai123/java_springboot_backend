package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users") // Base Route
public class UserController {

    private final UserService userService;

    private String UPLOAD_DIR = System.getProperty("user.dir")+"/uploads";
    // Dependency Injection (Spring connects Controller to Service)
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) 
                    ? Sort.by(sortBy).ascending() 
                    : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> userPage = userService.getAllUsers(pageable);
        return ResponseEntity.ok(userPage);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@Valid @ModelAttribute User user, @RequestParam("imageFile") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Please upload a profile picture"));
        }
        try {
            File uploadDirFolder = new File(UPLOAD_DIR);
            if (!uploadDirFolder.exists()) {
                uploadDirFolder.mkdirs(); 
            }
            String originalFileName = file.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID().toString()+originalFileName.substring(originalFileName.lastIndexOf("."));
            
            // 4. Save the file to the local server storage file system
            String targetPath = Paths.get(UPLOAD_DIR, uniqueFileName).toString();
            file.transferTo(new File(targetPath));

            // 5. Construct a publicly accessible web link for the image asset
            String fileDownloadUrl = "/uploads/" + uniqueFileName;
            user.setProfileImage(fileDownloadUrl);
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Profile created successfully!",
                    "username", user,
                    "profilePictureUrl", fileDownloadUrl
            ));
        } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to upload file: " + e.getMessage()));
        }
        // return userService.saveUser(user, file);
    }
}