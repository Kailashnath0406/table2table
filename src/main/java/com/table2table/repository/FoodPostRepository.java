package com.table2table.repository;

import com.table2table.model.FoodPost;
import com.table2table.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodPostRepository extends JpaRepository<FoodPost, Long> {
    List<FoodPost> findByUser(User user);
}

