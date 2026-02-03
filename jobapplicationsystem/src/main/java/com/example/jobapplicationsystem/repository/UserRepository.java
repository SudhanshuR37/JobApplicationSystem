package com.example.jobapplicationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.example.jobapplicationsystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
