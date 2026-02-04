package com.example.jobapplicationsystem.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import com.example.jobapplicationsystem.service.ApplicationService;

import com.example.jobapplicationsystem.dto.request.ApplyJobRequest;
import com.example.jobapplicationsystem.dto.response.ApplicationResponse;

import com.example.jobapplicationsystem.entity.Application;
import com.example.jobapplicationsystem.entity.User;

import com.example.jobapplicationsystem.mapper.ApplicationMapper;

import org.springframework.security.core.Authentication;
import com.example.jobapplicationsystem.security.CustomUserDetails;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ApplicationResponse applyToJob(
            @Valid @RequestBody ApplyJobRequest request,
            Authentication authentication
    ) {
        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();

        request.setCandidateId(user.getId());

        Application application = applicationService.applyToJob(request);
        return ApplicationMapper.toResponse(application);
    }

    @GetMapping
    public Page<ApplicationResponse> getMyApplications(
            Authentication authentication,
            Pageable pageable
    ) {
        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();

        return applicationService.getApplicationsByUser(user.getId(), pageable)
                .map(ApplicationMapper::toResponse);
    }

    @PutMapping("/{applicationId}/withdraw")
    public ApplicationResponse withdrawApplication(
            @PathVariable Long applicationId,
            Authentication authentication
    ) {
        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();

        Application application =
                applicationService.withdrawApplication(applicationId, user.getId());

        return ApplicationMapper.toResponse(application);
    }
}
