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

import com.example.jobapplicationsystem.mapper.ApplicationMapper;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ApplicationResponse applyToJob(@Valid @RequestBody ApplyJobRequest request) {
        Application application = applicationService.applyToJob(request);
        return ApplicationMapper.toResponse(application);
    }

    @GetMapping("/user/{userId}")
    public Page<ApplicationResponse> getApplicationsByUser(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        return applicationService.getApplicationsByUser(userId, pageable)
                .map(ApplicationMapper::toResponse);
    }

    @PutMapping("/{applicationId}/withdraw")
    public ApplicationResponse withdrawApplication(
            @PathVariable Long applicationId,
            @RequestParam Long candidateId
    ) {
        Application application =
                applicationService.withdrawApplication(applicationId, candidateId);

        return ApplicationMapper.toResponse(application);
    }
}
