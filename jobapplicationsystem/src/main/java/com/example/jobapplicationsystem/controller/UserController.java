package com.example.jobapplicationsystem.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.jobapplicationsystem.service.UserService;

import com.example.jobapplicationsystem.entity.User;

import com.example.jobapplicationsystem.mapper.UserMapper;

import com.example.jobapplicationsystem.dto.request.CreateUserRequest;

import com.example.jobapplicationsystem.dto.response.UserResponse;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = userService.createUser(request);
        return UserMapper.toResponse(user);
    }
}
