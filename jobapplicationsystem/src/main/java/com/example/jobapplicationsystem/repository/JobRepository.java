package com.example.jobapplicationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.example.jobapplicationsystem.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCreatedById(Long recruiterId);
}
