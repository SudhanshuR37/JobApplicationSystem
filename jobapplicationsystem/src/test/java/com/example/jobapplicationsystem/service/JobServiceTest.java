package com.example.jobapplicationsystem.service;

import com.example.jobapplicationsystem.dto.request.CreateJobRequest;
import com.example.jobapplicationsystem.entity.Job;
import com.example.jobapplicationsystem.entity.User;
import com.example.jobapplicationsystem.enums.Role;
import com.example.jobapplicationsystem.exception.ResourceNotFoundException;
import com.example.jobapplicationsystem.repository.JobRepository;
import com.example.jobapplicationsystem.repository.UserRepository;
import com.example.jobapplicationsystem.service.JobService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JobService jobService;

    @Test
    void shouldCreateJobSuccessfully() {
        CreateJobRequest request = new CreateJobRequest();
        request.setTitle("Backend Dev");
        request.setDescription("Spring Boot role");
        request.setCompany("Acme");
        request.setRecruiterId(1L);

        User recruiter = new User();
        recruiter.setId(1L);
        recruiter.setRole(Role.RECRUITER);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(recruiter));

        when(jobRepository.save(any(Job.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Job job = jobService.createJob(request);

        assertNotNull(job);
        assertEquals("Backend Dev", job.getTitle());
        assertEquals("Acme", job.getCompany());
        assertEquals(recruiter, job.getCreatedBy());
    }

    @Test
    void shouldThrowIfRecruiterNotFound() {
        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        CreateJobRequest request = new CreateJobRequest();
        request.setRecruiterId(1L);

        assertThrows(
                ResourceNotFoundException.class,
                () -> jobService.createJob(request)
        );
    }
}
