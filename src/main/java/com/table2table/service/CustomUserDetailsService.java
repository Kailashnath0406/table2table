package com.table2table.service;

import com.table2table.dto.CustomUserDetailsDto;
import com.table2table.model.User;
import com.table2table.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // Or UserService if you prefer

    @Override
    public CustomUserDetailsDto loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        user.setRole("ROLE_"+user.getRole());
        return new CustomUserDetailsDto(user); // ✅ injects role like "ADMIN"
    }
}
