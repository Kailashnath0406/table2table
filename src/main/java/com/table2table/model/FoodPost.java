package com.table2table.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "food_posts")
public class FoodPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Integer quantity;
    private LocalDateTime expiresAt;
    private String status; // ACTIVE, EXPIRED, CLAIMED

    @ManyToOne
    @JoinColumn(name = "posted_by")
    private User postedBy;

    @OneToMany(mappedBy = "foodPost", cascade = CascadeType.ALL)
    private List<FoodRequest> requests;

}
