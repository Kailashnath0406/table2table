package com.table2table.controller;

import com.table2table.dto.UserResponseDto;
import com.table2table.model.User;
import com.table2table.service.UserManagementService;
import com.table2table.util.UserManagementUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@EnableMethodSecurity
public class UserController {

    private final UserManagementService userManagementService;

    // Admin-only: Get all users
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        System.out.println("Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        List<UserResponseDto> allUsersResponse = UserManagementUtil.convertToDtoList(userManagementService.getAllUsers());
        return ResponseEntity.ok(allUsersResponse);
    }

    // Admin or owner: Get specific user
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.user.id")
    @GetMapping("getUser/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userManagementService.getUserById(id);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserResponseDto userResponse = UserManagementUtil.convertToDto(userOptional.get());
        return ResponseEntity.ok(userResponse);
    }

    // Admin or owner: Update user
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.user.id")
    @PutMapping("update/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody User updatedUser
    ) {
        UserResponseDto userResponse = UserManagementUtil.convertToDto(userManagementService.updateUser(id, updatedUser));
        return ResponseEntity.ok(userResponse);
    }

    // Admin-only: Delete user
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        userManagementService.deleteUser(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted successfully");

        return ResponseEntity.ok(response);
    }
}
