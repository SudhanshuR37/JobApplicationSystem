package com.example.jobapplicationsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApplicationResponse {
    private Long id;
    private String jobTitle;
    private String company;
    private String status;
    private LocalDateTime appliedAt;
}
