package com.table2table.util;

import com.table2table.dto.FoodRequestResponseDto;
import com.table2table.model.FoodRequest;

public class DtoConverterUtil {
    public static FoodRequestResponseDto convertToFoodRequestResponseDto(FoodRequest request) {
        FoodRequestResponseDto dto = new FoodRequestResponseDto();
        dto.setId(request.getId());
        dto.setFoodPostId(request.getFoodPost().getId());
        dto.setFoodTitle(request.getFoodPost().getTitle());
        dto.setPrice(request.getFoodPost().getPrice());
        dto.setQuantity(request.getFoodPost().getQuantity());

        dto.setCookId(request.getFoodPost().getUser().getId());
        dto.setCookName(request.getFoodPost().getUser().getName());

        dto.setCustomerId(request.getRequestedBy().getId());
        dto.setCustomerName(request.getRequestedBy().getName());

        dto.setStatus(request.getStatus());
        dto.setRejectionReason(request.getRejectionReason());
        dto.setRequestedAt(request.getRequestedAt());

        return dto;
    }
}
