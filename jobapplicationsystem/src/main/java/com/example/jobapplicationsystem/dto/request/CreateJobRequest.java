import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class CreateJobRequest {

    @NotBlank(message = "Job title cannot be blank")
    private String title;

    @NotBlank(message = "Job description cannot be blank")
    @Size(max = 2000, message = "Description too long")
    private String description;

    @NotBlank(message = "Company name cannot be blank")
    private String company;

    @NotNull(message = "Recruiter ID is required")
    private Long recruiterId;
}