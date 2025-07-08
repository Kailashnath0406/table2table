package com.table2table.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "food_requests")
public class FoodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // PENDING, ACCEPTED, REJECTED

    @ManyToOne
    @JoinColumn(name = "food_post_id")
    private FoodPost foodPost;

    @ManyToOne
    @JoinColumn(name = "requested_by")
    private User requestedBy;

}
