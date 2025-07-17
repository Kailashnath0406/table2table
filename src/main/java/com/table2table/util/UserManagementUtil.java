package com.table2table.util;

import com.table2table.dto.FoodPostResponse;
import com.table2table.dto.UserResponseDto;
import com.table2table.model.FoodPost;
import com.table2table.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserManagementUtil {

    public static UserResponseDto convertToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setFlatNumber(user.getFlatNumber());
        dto.setFloor(user.getFloor());
        dto.setRole(user.getRole());
        return dto;
    }

    public static List<UserResponseDto> convertToDtoList(List<User> users) {
        return users.stream()
                .map(UserManagementUtil::convertToDto)
                .collect(Collectors.toList());
    }

    public static FoodPostResponse convertToFoodPostDto(FoodPost foodPost) {
        FoodPostResponse dto = new FoodPostResponse();
        dto.setId(foodPost.getId());
        dto.setTitle(foodPost.getTitle());
        dto.setDescription(foodPost.getDescription());
        dto.setPrice(foodPost.getPrice());
        dto.setQuantity(foodPost.getQuantity());
        dto.setExpiresAt(foodPost.getExpiresAt());
        dto.setImageUrl(foodPost.getImageUrl());
        dto.setStatus(foodPost.getStatus());

        // Assuming postedBy is a User and you want to show the user's name or email
        dto.setPostedBy(foodPost.getUser().getName()); // or .getEmail()

        return dto;
    }

}
