package com.table2table.dto;

import com.table2table.model.User;
import com.table2table.model.enums.FoodStatus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class FoodPostResponse {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private Integer quantity;
    private LocalDateTime expiresAt;
    private String imageUrl;
    private FoodStatus status;
    private String postedBy; // maybe postedBy user name or email
}
