package com.table2table.dto;

import com.table2table.model.enums.FoodStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FoodPostFilterRequest {
    private FoodStatus status;
    private Double minPrice;
    private Double maxPrice;
    private Long communityId;
    private String keyword;
    private LocalDateTime expiresBefore;
    private LocalDateTime expiresAfter;
    private String postedByEmail;
    private String sortBy = "createdAt"; // default sorting
    private String sortDirection = "desc"; // "asc" or "desc"
    private Integer page = 0;
    private Integer size = 10;
}
