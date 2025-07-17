package com.table2table.service.impl;

import com.table2table.dto.CreateFoodRequestDto;
import com.table2table.dto.FoodRequestResponseDto;
import com.table2table.exception.InsufficientQuantityException;
import com.table2table.exception.ResourceNotFoundException;
import com.table2table.model.FoodPost;
import com.table2table.model.FoodRequest;
import com.table2table.model.User;
import com.table2table.model.enums.RequestStatus;
import com.table2table.repository.FoodPostRepository;
import com.table2table.repository.FoodRequestRepository;
import com.table2table.repository.UserRepository;
import com.table2table.security.JwtService;
import com.table2table.service.FoodRequestService;
import com.table2table.util.DtoConverterUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodRequestServiceImpl implements FoodRequestService {

    private final FoodPostRepository foodPostRepository;
    private final FoodRequestRepository foodRequestRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    @Transactional
    public FoodRequestResponseDto createRequest(CreateFoodRequestDto dto, String authHeader) {
        Long customerId = jwtService.extractUserId(authHeader);
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        FoodPost foodPost = foodPostRepository.findById(dto.getFoodPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Food post not found"));

        if (foodPost.getQuantity() <= 0) {
            throw new RuntimeException("Food not available");
        }

        // ✅ MOCK PAYMENT SIMULATION
        boolean paymentSuccess = simulatePayment(foodPost.getPrice());
        if (!paymentSuccess) {
            throw new RuntimeException("Payment failed");
        }

        FoodRequest foodRequest = new FoodRequest(dto.getQuantity(),RequestStatus.PENDING,null,LocalDateTime.now(),paymentSuccess,foodPost,customer);

        foodRequestRepository.save(foodRequest);
        return DtoConverterUtil.convertToFoodRequestResponseDto(foodRequest);
    }

    @Override
    public List<FoodRequestResponseDto> getRequestsForCook(Long cookId) {
        List<FoodRequest> requests = foodRequestRepository.findByFoodPost_User_Id(cookId);
        return requests.stream()
                .map(DtoConverterUtil::convertToFoodRequestResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FoodRequestResponseDto updateRequestStatus(Long requestId, RequestStatus status, String rejectionReason, String authHeader) {
        Long cookId = jwtService.extractUserId(authHeader);

        FoodRequest foodRequest = foodRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        if (!foodRequest.getFoodPost().getUser().getId().equals(cookId)) {
            throw new RuntimeException("Unauthorized");
        }

        if (status == RequestStatus.REJECTED || status == RequestStatus.REFUND_INITIATED) {
            foodRequest.setStatus(RequestStatus.REFUND_INITIATED);
            foodRequest.setRejectionReason(rejectionReason != null ? rejectionReason : "No reason provided");

            // Simulate refund
            simulateRefund(foodRequest.getFoodPost().getPrice());

            // Increase the quantity back
            FoodPost post = foodRequest.getFoodPost();
            post.setQuantity(post.getQuantity() + 1);
            foodPostRepository.save(post);
        } else if (status == RequestStatus.ACCEPTED) {
            FoodPost foodPost = foodPostRepository.findById(foodRequest.getFoodPost().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Food post not found"));
            if(foodRequest.getQuantity()<foodPost.getQuantity()) {
                foodPost.setQuantity(foodPost.getQuantity() - foodRequest.getQuantity());
            }
            else{
                throw new InsufficientQuantityException("requested quantity is more than what we have");
            }
            foodRequest.setStatus(RequestStatus.ACCEPTED);
        }

        foodRequestRepository.save(foodRequest);
        return DtoConverterUtil.convertToFoodRequestResponseDto(foodRequest);
    }

    // 🔁 MOCK PAYMENT FUNCTION
    private boolean simulatePayment(Double amount) {
        System.out.println("Processing mock payment of ₹" + amount + " ...");
        return true; // always succeed for mock
    }

    // 🔁 MOCK REFUND FUNCTION
    private void simulateRefund(Double amount) {
        System.out.println("Processing mock refund of ₹" + amount + " ...");
    }
}
