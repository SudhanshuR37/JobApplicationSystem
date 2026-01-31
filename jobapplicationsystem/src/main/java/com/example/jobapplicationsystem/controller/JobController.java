import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public Job createJob(@Valid @RequestBody CreateJobRequest request) {
        return jobService.createJob(request);
    }

    @GetMapping
    public Page<JobResponse> getJobs(Pageable pageable) {
        return jobService.getJobs(pageable)
                .map(JobMapper::toResponse);
    }
}
