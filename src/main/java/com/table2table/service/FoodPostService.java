package com.table2table.service;

import com.table2table.dto.FoodPostFilterRequest;
import com.table2table.dto.FoodPostRequest;
import com.table2table.dto.FoodPostResponse;
import com.table2table.dto.UpdateFoodPostRequest;
import com.table2table.exception.ResourceNotFoundException;
import com.table2table.model.FoodPost;
import com.table2table.model.User;
import com.table2table.model.enums.Role;
import com.table2table.repository.FoodPostRepository;
import com.table2table.repository.UserRepository;
import com.table2table.util.UserManagementUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodPostService {

    private final FoodPostRepository foodPostRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    public void createFoodPost(FoodPostRequest foodRequset, User user) {
        FoodPost post = new FoodPost();
        post.setTitle(foodRequset.getTitle());
        post.setDescription(foodRequset.getDescription());
        post.setPrice(foodRequset.getPrice());
        post.setQuantity(foodRequset.getQuantity());
        post.setImageUrl(foodRequset.getImageUrl());
        post.setExpiresAt(foodRequset.getExpiresAt());
        post.setStatus(foodRequset.getStatus());
        post.setUser(user);
        foodPostRepository.save(post);
    }

    public Page<FoodPostResponse> filterFoodPosts(FoodPostFilterRequest filter) {
        Pageable pageable = PageRequest.of(
                filter.getPage(),
                filter.getSize(),
                Sort.by(Sort.Direction.fromString(filter.getSortDirection()), filter.getSortBy())
        );

        Specification<FoodPost> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }

            if (filter.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }

            if (filter.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }

            if (filter.getCommunityId() != null) {
                predicates.add(cb.equal(root.get("user").get("community").get("id"), filter.getCommunityId()));
            }

            if (filter.getKeyword() != null && !filter.getKeyword().isEmpty()) {
                Predicate titlePredicate = cb.like(cb.lower(root.get("title")), "%" + filter.getKeyword().toLowerCase() + "%");
                Predicate descPredicate = cb.like(cb.lower(root.get("description")), "%" + filter.getKeyword().toLowerCase() + "%");
                predicates.add(cb.or(titlePredicate, descPredicate));
            }

            if (filter.getExpiresBefore() != null) {
                predicates.add(cb.lessThan(root.get("expiresAt"), filter.getExpiresBefore()));
            }

            if (filter.getExpiresAfter() != null) {
                predicates.add(cb.greaterThan(root.get("expiresAt"), filter.getExpiresAfter()));
            }

            if (filter.getPostedByEmail() != null) {
                predicates.add(cb.equal(root.get("user").get("email"), filter.getPostedByEmail()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<FoodPost> posts = foodPostRepository.findAll(spec, pageable);
        return posts.map(UserManagementUtil::convertToFoodPostDto);
    }

    public FoodPostResponse getFoodPostById(Long id) {
        FoodPost post = foodPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FoodPost not found with id " + id));

        return UserManagementUtil.convertToFoodPostDto(post);
    }

    public FoodPostResponse updateFoodPost(Long id, FoodPostRequest request, String userEmail, MultipartFile file) throws IOException {
        FoodPost foodPost = foodPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FoodPost not found"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Allow if admin or owner
        if (!foodPost.getUser().getId().equals(user.getId()) && !user.getRole().equals("ADMIN")) {
            throw new AccessDeniedException("You do not have permission to update this post");
        }
        if (file != null && !file.isEmpty()) {
            String newImageUrl = cloudinaryService.uploadImage(file); // implement this
            foodPost.setImageUrl(newImageUrl);
        }

        foodPost.setTitle(request.getTitle());
        foodPost.setDescription(request.getDescription());
        //foodPost.setLocation(request.getLocation());
        foodPost.setQuantity(request.getQuantity());
        foodPost.setExpiresAt(request.getExpiresAt());
        foodPost.setStatus(request.getStatus());
        foodPost.setPrice(request.getPrice());

        foodPostRepository.save(foodPost);

        return UserManagementUtil.convertToFoodPostDto(foodPost);
    }

    public void deleteFoodPost(Long id, String userEmail) {
        FoodPost post = foodPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FoodPost not found"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!post.getUser().getId().equals(user.getId()) && !user.getRole().equals("ADMIN")) {
            throw new AccessDeniedException("You do not have permission to delete this post");
        }

        foodPostRepository.delete(post);
    }

}
