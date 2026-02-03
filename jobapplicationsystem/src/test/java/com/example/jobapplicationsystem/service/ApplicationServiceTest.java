package com.example.jobapplicationsystem.service;

import com.example.jobapplicationsystem.dto.request.ApplyJobRequest;
import com.example.jobapplicationsystem.entity.Application;
import com.example.jobapplicationsystem.entity.Job;
import com.example.jobapplicationsystem.entity.User;
import com.example.jobapplicationsystem.enums.ApplicationStatus;
import com.example.jobapplicationsystem.enums.Role;
import com.example.jobapplicationsystem.exception.DuplicateApplicationException;
import com.example.jobapplicationsystem.exception.InvalidApplicationStateException;
import com.example.jobapplicationsystem.exception.ResourceNotFoundException;
import com.example.jobapplicationsystem.repository.ApplicationRepository;
import com.example.jobapplicationsystem.repository.JobRepository;
import com.example.jobapplicationsystem.repository.UserRepository;
import com.example.jobapplicationsystem.service.ApplicationService;

import org.junit.jupiter.api.BeforeEach;
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
class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ApplicationService applicationService;

    private Job job;
    private User candidate;

    @BeforeEach
    void setup() {
        job = new Job();
        job.setId(1L);

        candidate = new User();
        candidate.setId(2L);
        candidate.setRole(Role.CANDIDATE);
    }

    @Test
    void shouldApplyToJobSuccessfully() {
        ApplyJobRequest request = new ApplyJobRequest();
        request.setJobId(1L);
        request.setCandidateId(2L);

        when(applicationRepository
                .existsByJobIdAndCandidateId(1L, 2L))
                .thenReturn(false);

        when(jobRepository.findById(1L))
                .thenReturn(Optional.of(job));

        when(userRepository.findById(2L))
                .thenReturn(Optional.of(candidate));

        when(applicationRepository.save(any(Application.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Application app = applicationService.applyToJob(request);

        assertNotNull(app);
        assertEquals(job, app.getJob());
        assertEquals(candidate, app.getCandidate());
        assertEquals(ApplicationStatus.APPLIED, app.getStatus());
        assertNotNull(app.getAppliedAt());
    }

    @Test
    void shouldThrowIfDuplicateApplication() {
        ApplyJobRequest request = new ApplyJobRequest();
        request.setJobId(1L);
        request.setCandidateId(2L);

        when(applicationRepository
                .existsByJobIdAndCandidateId(1L, 2L))
                .thenReturn(true);

        assertThrows(
                DuplicateApplicationException.class,
                () -> applicationService.applyToJob(request)
        );
    }

    @Test
    void shouldWithdrawApplicationSuccessfully() {
        Application application = new Application();
        application.setId(10L);
        application.setCandidate(candidate);
        application.setStatus(ApplicationStatus.APPLIED);

        when(applicationRepository.findByIdAndCandidateId(10L, 2L))
                .thenReturn(Optional.of(application));

        when(applicationRepository.save(any(Application.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Application withdrawn =
                applicationService.withdrawApplication(10L, 2L);

        assertEquals(ApplicationStatus.WITHDRAWN, withdrawn.getStatus());
    }

    @Test
    void shouldThrowIfAlreadyWithdrawn() {
        Application application = new Application();
        application.setId(10L);
        application.setCandidate(candidate);
        application.setStatus(ApplicationStatus.WITHDRAWN);

        when(applicationRepository.findByIdAndCandidateId(10L, 2L))
                .thenReturn(Optional.of(application));

        assertThrows(
                InvalidApplicationStateException.class,
                () -> applicationService.withdrawApplication(10L, 2L)
        );
    }

    @Test
    void shouldThrowIfApplicationNotOwnedByCandidate() {
        when(applicationRepository.findByIdAndCandidateId(10L, 2L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> applicationService.withdrawApplication(10L, 2L)
        );
    }
}
