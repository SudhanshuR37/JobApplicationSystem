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
