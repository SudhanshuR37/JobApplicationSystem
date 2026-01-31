import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

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
        boolean alreadyApplied = applicationRepository
                .findByJobIdAndCandidateId(
                        request.getJobId(),
                        request.getCandidateId()
                )
                .isPresent();

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
}
