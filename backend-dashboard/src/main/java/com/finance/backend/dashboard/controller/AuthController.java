package com.finance.backend.dashboard.controller;

import com.finance.backend.dashboard.model.Role;
import com.finance.backend.dashboard.model.User;
import com.finance.backend.dashboard.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }
        if (user.getRole() == null) user.setRole(Role.VIEWER); // only default if not provided
        user.setActive(true);
        userRepository.save(user);
        return ResponseEntity.status(201).body("Registered successfully");
    }
}