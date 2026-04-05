package com.finance.backend.dashboard.repository;

import com.finance.backend.dashboard.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Long> {
    boolean existsByEmail(@NotBlank(message = "Email cannot be blank") @Email(message = "Invalid Email Format") String email);

    Optional<User> findByEmail(String email);
}