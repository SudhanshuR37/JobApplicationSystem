package com.example.jobapplicationsystem.service;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import com.example.jobapplicationsystem.exception.ResourceNotFoundException;

import com.example.jobapplicationsystem.entity.Job;
import com.example.jobapplicationsystem.entity.User;

import com.example.jobapplicationsystem.repository.JobRepository;
import com.example.jobapplicationsystem.repository.UserRepository;

import com.example.jobapplicationsystem.dto.request.CreateJobRequest;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobService(JobRepository jobRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    public Job createJob(CreateJobRequest request) {
        User recruiter = userRepository.findById(request.getRecruiterId())
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found"));

        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCompany(request.getCompany());
        job.setCreatedBy(recruiter);

        return jobRepository.save(job);
    }

    public Page<Job> getJobs(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }
}
