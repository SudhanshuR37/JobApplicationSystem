import lombok.Data;

@Data
public class ApplyJobRequest {

    @NotNull(message = "Job ID is required")
    private Long jobId;

    @NotNull(message = "Candidate ID is required")
    private Long candidateId;
}
