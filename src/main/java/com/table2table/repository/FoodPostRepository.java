package com.table2table.repository;

import com.table2table.model.FoodPost;
import com.table2table.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FoodPostRepository extends JpaRepository<FoodPost, Long>, JpaSpecificationExecutor<FoodPost> {
    List<FoodPost> findByUser(User user);
}

