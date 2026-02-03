package com.example.jobapplicationsystem.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import com.example.jobapplicationsystem.entity.Job;

import com.example.jobapplicationsystem.service.ApplicationService;
import com.example.jobapplicationsystem.service.JobService;

import com.example.jobapplicationsystem.dto.request.CreateJobRequest;
import com.example.jobapplicationsystem.dto.response.JobResponse;

import com.example.jobapplicationsystem.mapper.JobMapper;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public Job createJob(@Valid @RequestBody CreateJobRequest request) {
        return jobService.createJob(request);
    }

    @GetMapping
    public Page<JobResponse> getJobs(Pageable pageable) {
        return jobService.getJobs(pageable)
                .map(JobMapper::toResponse);
    }
}
