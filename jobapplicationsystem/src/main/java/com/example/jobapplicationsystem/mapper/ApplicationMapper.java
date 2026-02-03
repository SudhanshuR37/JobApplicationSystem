package com.example.jobapplicationsystem.mapper;

import com.example.jobapplicationsystem.entity.Application;

import com.example.jobapplicationsystem.dto.response.ApplicationResponse;

public class ApplicationMapper {

    public static ApplicationResponse toResponse(Application app) {
        return new ApplicationResponse(
                app.getId(),
                app.getJob().getTitle(),
                app.getJob().getCompany(),
                app.getStatus().name(),
                app.getAppliedAt()
        );
    }
}
