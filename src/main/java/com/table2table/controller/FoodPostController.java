package com.table2table.controller;

import com.table2table.dto.FoodPostRequest;
import com.table2table.model.User;
import com.table2table.repository.UserRepository;
import com.table2table.security.JwtService;
import com.table2table.service.CloudinaryService;
import com.table2table.service.FoodPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodPostController {

    private final FoodPostService foodPostService;
    private final CloudinaryService cloudinaryService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<String> createFoodPost(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute FoodPostRequest foodPostRequest,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            // Extract email from token
            String token = authHeader.substring(7);
            String email = jwtService.extractUsername(token); // call your jwtService

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Upload image to Cloudinary
            String imageUrl = cloudinaryService.uploadImage(file);

            foodPostRequest.setImageUrl(imageUrl);
            // Create post
            foodPostService.createFoodPost(foodPostRequest, user);

            return ResponseEntity.ok("Food post created successfully");

        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }
}

