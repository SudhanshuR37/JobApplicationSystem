package com.example.jobapplicationsystem.repository;

import com.example.jobapplicationsystem.entity.Job;
import com.example.jobapplicationsystem.entity.User;
import com.example.jobapplicationsystem.enums.Role;
import com.example.jobapplicationsystem.repository.JobRepository;
import com.example.jobapplicationsystem.repository.UserRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class JobRepositoryTest {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveJobWithRecruiter() {
        User recruiter = new User();
        recruiter.setName("Alice");
        recruiter.setEmail("alice@test.com");
        recruiter.setPassword("encoded");
        recruiter.setRole(Role.RECRUITER);

        recruiter = userRepository.save(recruiter);

        Job job = new Job();
        job.setTitle("Backend Dev");
        job.setCompany("Acme");
        job.setDescription("Spring Boot");
        job.setCreatedBy(recruiter);

        Job saved = jobRepository.save(job);

        assertNotNull(saved.getId());
        assertEquals("Alice", saved.getCreatedBy().getName());
    }
}
