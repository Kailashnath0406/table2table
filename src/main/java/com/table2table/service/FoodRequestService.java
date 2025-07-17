package com.table2table.service;

import com.table2table.dto.CreateFoodRequestDto;
import com.table2table.dto.FoodRequestResponseDto;
import com.table2table.model.enums.RequestStatus;

import java.util.List;

public interface FoodRequestService {
    FoodRequestResponseDto createRequest(CreateFoodRequestDto dto, String authHeader);

    List<FoodRequestResponseDto> getRequestsForCook(Long cookId);

    FoodRequestResponseDto updateRequestStatus(Long requestId, RequestStatus status, String rejectionReason, String authHeader);

    //List<FoodRequestResponseDto> getRequestsByUser(Long userId);
}
