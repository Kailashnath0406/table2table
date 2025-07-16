package com.table2table.service;

import com.table2table.dto.FoodPostRequest;
import com.table2table.model.FoodPost;
import com.table2table.model.User;
import com.table2table.repository.FoodPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodPostService {

    private final FoodPostRepository foodPostRepository;

    public void createFoodPost(FoodPostRequest foodRequset, User user) {
        FoodPost post = new FoodPost();
        post.setTitle(foodRequset.getTitle());
        post.setDescription(foodRequset.getDescription());
        post.setPrice(foodRequset.getPrice());
        post.setQuantity(foodRequset.getQuantity());
        post.setImageUrl(foodRequset.getImageUrl());
        post.setExpiresAt(foodRequset.getExpiresAt());
        post.setStatus(foodRequset.getStatus());
        post.setUser(user);
        foodPostRepository.save(post);
    }
}
