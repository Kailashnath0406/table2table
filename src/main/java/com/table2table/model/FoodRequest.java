package com.table2table.model;

import com.table2table.model.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "food_requests")
public class FoodRequest {
    public FoodRequest(int quantity, RequestStatus status, String rejectionReason, LocalDateTime requestedAt, boolean paymentSuccessful, FoodPost foodPost, User requestedBy) {
        this.quantity = quantity;
        this.status = status;
        this.rejectionReason = rejectionReason;
        this.requestedAt = requestedAt;
        this.paymentSuccessful = paymentSuccessful;
        this.foodPost = foodPost;
        this.requestedBy = requestedBy;
    }

    public FoodRequest() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private RequestStatus status; // PENDING, ACCEPTED, REJECTED, REFUND_INITIATED

    private String rejectionReason;

    private LocalDateTime requestedAt;

    private boolean paymentSuccessful;

    @ManyToOne
    @JoinColumn(name = "food_post_id")
    private FoodPost foodPost;

    @ManyToOne
    @JoinColumn(name = "requested_by")
    private User requestedBy;

}
