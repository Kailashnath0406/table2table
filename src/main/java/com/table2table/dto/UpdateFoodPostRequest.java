package com.table2table.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateFoodPostRequest {
    private String title;
    private String description;
    private String location;
    private Integer quantity;
    private LocalDateTime expiresAt;
}
