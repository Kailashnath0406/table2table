package com.table2table.model;

import com.table2table.model.enums.FoodStatus;
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
    private Double price;
    private Integer quantity;
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodStatus status; // ACTIVE, EXPIRED, CLAIMED

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "foodPost", cascade = CascadeType.ALL)
    private List<FoodRequest> requests;

}
