@DataJpaTest
class ApplicationRepositoryTest {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    private Job job;
    private User candidate;

    @BeforeEach
    void setup() {
        User recruiter = new User();
        recruiter.setName("Recruiter");
        recruiter.setEmail("rec@test.com");
        recruiter.setPassword("pass");
        recruiter.setRole(Role.RECRUITER);
        recruiter = userRepository.save(recruiter);

        job = new Job();
        job.setTitle("Java Dev");
        job.setCompany("TechCo");
        job.setDescription("Backend");
        job.setCreatedBy(recruiter);
        job = jobRepository.save(job);

        candidate = new User();
        candidate.setName("Candidate");
        candidate.setEmail("cand@test.com");
        candidate.setPassword("pass");
        candidate.setRole(Role.CANDIDATE);
        candidate = userRepository.save(candidate);
    }

    @Test
    void shouldFindByJobIdAndCandidateId() {
        Application application = new Application();
        application.setJob(job);
        application.setCandidate(candidate);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setAppliedAt(LocalDateTime.now());

        applicationRepository.save(application);

        Optional<Application> found =
                applicationRepository.findByJobIdAndCandidateId(
                        job.getId(), candidate.getId()
                );

        assertTrue(found.isPresent());
    }

    @Test
    void shouldReturnApplicationsByCandidateWithPagination() {
        Application application = new Application();
        application.setJob(job);
        application.setCandidate(candidate);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setAppliedAt(LocalDateTime.now());

        applicationRepository.save(application);

        Page<Application> page =
                applicationRepository.findByCandidateId(
                        candidate.getId(),
                        PageRequest.of(0, 10)
                );

        assertEquals(1, page.getTotalElements());
    }

    @Test
    void shouldFailOnDuplicateApplication() {
        Application a1 = new Application();
        a1.setJob(job);
        a1.setCandidate(candidate);
        a1.setStatus(ApplicationStatus.APPLIED);
        a1.setAppliedAt(LocalDateTime.now());

        applicationRepository.save(a1);

        Application a2 = new Application();
        a2.setJob(job);
        a2.setCandidate(candidate);
        a2.setStatus(ApplicationStatus.APPLIED);
        a2.setAppliedAt(LocalDateTime.now());

        assertThrows(
                DataIntegrityViolationException.class,
                () -> applicationRepository.saveAndFlush(a2)
        );
    }

}
