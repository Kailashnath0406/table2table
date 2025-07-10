package com.table2table.controller;

import com.table2table.dto.JwtResponse;
import com.table2table.dto.LoginRequest;
import com.table2table.dto.RegisterRequest;
import com.table2table.dto.UserResponseDto;
import com.table2table.model.User;
import com.table2table.repository.UserRepository;
import com.table2table.security.JwtService;
import com.table2table.service.UserService;
import com.table2table.util.UserManagementUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserService userService,
            JwtService jwtService,
            UserRepository userRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        String response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //String token = jwtService.generateToken(loginRequest.getEmail());

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(token, user.getEmail(), user.getRole()));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        // Fetch user from database using email
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserResponseDto userResponse = UserManagementUtil.convertToDto(user);

        return ResponseEntity.ok(userResponse);
    }
}

