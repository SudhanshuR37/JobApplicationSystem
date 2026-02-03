package com.example.jobapplicationsystem.repository;

import com.example.jobapplicationsystem.entity.User;
import com.example.jobapplicationsystem.enums.Role;
import com.example.jobapplicationsystem.repository.UserRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUserByEmail() {
        User user = new User();
        user.setName("John");
        user.setEmail("john@test.com");
        user.setPassword("encoded");
        user.setRole(Role.CANDIDATE);

        userRepository.save(user);

        Optional<User> found =
                userRepository.findByEmail("john@test.com");

        assertTrue(found.isPresent());
        assertEquals("John", found.get().getName());
    }
}
