package com.example.jobapplicationsystem.service;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

import com.example.jobapplicationsystem.exception.DuplicateApplicationException;
import com.example.jobapplicationsystem.exception.ResourceNotFoundException;
import com.example.jobapplicationsystem.exception.InvalidApplicationStateException;

import com.example.jobapplicationsystem.entity.Application;
import com.example.jobapplicationsystem.entity.Job;
import com.example.jobapplicationsystem.entity.User;

import com.example.jobapplicationsystem.enums.ApplicationStatus;

import com.example.jobapplicationsystem.repository.ApplicationRepository;
import com.example.jobapplicationsystem.repository.JobRepository;
import com.example.jobapplicationsystem.repository.UserRepository;

import com.example.jobapplicationsystem.dto.request.ApplyJobRequest;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              JobRepository jobRepository,
                              UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    public Application applyToJob(ApplyJobRequest request) {
        boolean alreadyApplied =
                applicationRepository.existsByJobIdAndCandidateId(
                        request.getJobId(),
                        request.getCandidateId()
                );

        if (alreadyApplied) {
            throw new DuplicateApplicationException(
                    "Candidate has already applied to this job"
            );
        }

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        User candidate = userRepository.findById(request.getCandidateId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found"));

        Application application = new Application();
        application.setJob(job);
        application.setCandidate(candidate);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setAppliedAt(LocalDateTime.now());

        return applicationRepository.save(application);
    }

    public Page<Application> getApplicationsByUser(Long userId, Pageable pageable) {
        return applicationRepository.findByCandidateId(userId, pageable);
    }

    public Application withdrawApplication(Long applicationId, Long candidateId) {
        Application application = applicationRepository
                .findByIdAndCandidateId(applicationId, candidateId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Application not found for this candidate"
                ));

        if(application.getStatus() == ApplicationStatus.WITHDRAWN) {
            throw new InvalidApplicationStateException("Application already withdrawn");
        }

        application.setStatus(ApplicationStatus.WITHDRAWN);
        application.setAppliedAt(application.getAppliedAt());

        return applicationRepository.save(application);
    }
}
