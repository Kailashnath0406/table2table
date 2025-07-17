package com.table2table.controller;

import com.table2table.dto.FoodPostFilterRequest;
import com.table2table.dto.FoodPostRequest;
import com.table2table.dto.FoodPostResponse;
import com.table2table.dto.UpdateFoodPostRequest;
import com.table2table.model.User;
import com.table2table.repository.UserRepository;
import com.table2table.security.JwtService;
import com.table2table.service.CloudinaryService;
import com.table2table.service.FoodPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodPostController {

    private final FoodPostService foodPostService;
    private final CloudinaryService cloudinaryService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('COOK')")
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

    @PostMapping("/fetchFoodPostsWithFilter")
    public ResponseEntity<Page<FoodPostResponse>> filterFoodPosts(
            @RequestBody FoodPostFilterRequest filterRequest
    ) {
        Page<FoodPostResponse> result = foodPostService.filterFoodPosts(filterRequest);
        return ResponseEntity.ok(result);
    }

    @GetMapping("fetchPostById/{id}")
    public ResponseEntity<FoodPostResponse> getFoodPostById(@PathVariable Long id) {
        FoodPostResponse response = foodPostService.getFoodPostById(id);
        return ResponseEntity.ok(response);
    }
    @PutMapping("updateFoodPost/{id}")
    public ResponseEntity<FoodPostResponse> updateFoodPost(
            @PathVariable Long id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @ModelAttribute FoodPostRequest request,
            @RequestHeader("Authorization") String authHeader) throws IOException {

        String token = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(token);

        FoodPostResponse response = foodPostService.updateFoodPost(id, request, userEmail,file);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("deletePost/{id}")
    public ResponseEntity<?> deleteFoodPost(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(token);

        foodPostService.deleteFoodPost(id, userEmail);
        return ResponseEntity.ok("Food post deleted successfully.");
    }

}

