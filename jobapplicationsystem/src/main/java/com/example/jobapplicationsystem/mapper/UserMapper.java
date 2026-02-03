package com.example.jobapplicationsystem.mapper;

import com.example.jobapplicationsystem.entity.User;

import com.example.jobapplicationsystem.dto.response.UserResponse;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
