package com.table2table.repository;

import com.table2table.model.FoodRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRequestRepository extends JpaRepository<FoodRequest, Long> {
    List<FoodRequest> findByFoodPost_User_Id(Long cookId);
}
