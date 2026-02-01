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
