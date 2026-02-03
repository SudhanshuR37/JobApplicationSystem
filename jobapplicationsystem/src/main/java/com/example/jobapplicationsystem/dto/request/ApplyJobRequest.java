package com.example.jobapplicationsystem.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class ApplyJobRequest {

    @NotNull(message = "Job ID is required")
    private Long jobId;

    @NotNull(message = "Candidate ID is required")
    private Long candidateId;
}
