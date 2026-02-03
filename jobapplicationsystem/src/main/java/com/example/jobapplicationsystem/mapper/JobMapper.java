package com.example.jobapplicationsystem.mapper;

import com.example.jobapplicationsystem.entity.Job;

import com.example.jobapplicationsystem.dto.response.JobResponse;

public class JobMapper {

    public static JobResponse toResponse(Job job) {
        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getCompany(),
                job.getCreatedBy().getName()
        );
    }
}
