package com.example.jobapplicationsystem.service;

import com.example.jobapplicationsystem.dto.request.CreateUserRequest;
import com.example.jobapplicationsystem.entity.User;
import com.example.jobapplicationsystem.enums.Role;
import com.example.jobapplicationsystem.repository.UserRepository;
import com.example.jobapplicationsystem.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUserSuccessfully() {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("John");
        request.setEmail("john@test.com");
        request.setPassword("password123");
        request.setRole(Role.CANDIDATE);

        when(passwordEncoder.encode("password123"))
                .thenReturn("encodedPass");

        when(userRepository.save(any(User.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        User user = userService.createUser(request);

        assertNotNull(user);
        assertEquals("John", user.getName());
        assertEquals("john@test.com", user.getEmail());
        assertEquals("encodedPass", user.getPassword());
        assertEquals(Role.CANDIDATE, user.getRole());
    }
}
