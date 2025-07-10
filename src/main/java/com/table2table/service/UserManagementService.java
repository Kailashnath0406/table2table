package com.table2table.service;

import com.table2table.model.User;

import java.util.List;
import java.util.Optional;

public interface UserManagementService {
    List<User> getAllUsers();                  // For Admin

    Optional<User> getUserById(Long id);

    User updateUser(Long id, User updatedUser);

    void deleteUser(Long id);

}
