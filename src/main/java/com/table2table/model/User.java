package com.table2table.model;

import jakarta.persistence.*;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String phone;
    private String flatNumber;
    private String floor;
    private String role;  // COOK, CUSTOMER, ADMIN

    // Relationships
    @OneToMany(mappedBy = "postedBy", cascade = CascadeType.ALL)
    private List<FoodPost> foodPosts;

    @OneToMany(mappedBy = "requestedBy", cascade = CascadeType.ALL)
    private List<FoodRequest> foodRequests;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

}

