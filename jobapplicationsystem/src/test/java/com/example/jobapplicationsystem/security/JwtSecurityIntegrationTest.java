@Autowired
private MockMvc mockMvc;

@Autowired
private UserRepository userRepository;

@Autowired
private PasswordEncoder passwordEncoder;

@BeforeEach
void setup() {
        userRepository.deleteAll();

        User candidate = new User();
        candidate.setName("Candidate");
        candidate.setEmail("candidate@test.com");
        candidate.setPassword(passwordEncoder.encode("password"));
        candidate.setRole(Role.CANDIDATE);

        User recruiter = new User();
        recruiter.setName("Recruiter");
        recruiter.setEmail("recruiter@test.com");
        recruiter.setPassword(passwordEncoder.encode("password"));
        recruiter.setRole(Role.RECRUITER);

        userRepository.save(candidate);
        userRepository.save(recruiter);
}

private String loginAndGetToken(String email) throws Exception {

        String loginJson = """
        {
          "email": "%s",
          "password": "password"
        }
        """.formatted(email);

        MvcResult result = mockMvc.perform(
        post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginJson)
        )
        .andExpect(status().isOk())
        .andReturn();

        return result.getResponse().getContentAsString();
}

@Test
void shouldLoginAndReturnJwtToken() throws Exception {

        String token = loginAndGetToken("candidate@test.com");

        assertNotNull(token);
        assertTrue(token.startsWith("ey"));
}

@Test
void shouldRejectAccessWithoutJwt() throws Exception {

        mockMvc.perform(get("/applications"))
        .andExpect(status().isUnauthorized());
}

@Test
void candidateShouldAccessApplications() throws Exception {

        String token = loginAndGetToken("candidate@test.com");

        mockMvc.perform(
        get("/applications")
        .header("Authorization", "Bearer " + token)
        )
        .andExpect(status().isOk());
}

@Test
void candidateShouldNotCreateJob() throws Exception {

        String token = loginAndGetToken("candidate@test.com");

        mockMvc.perform(
        post("/jobs")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{}")
        )
        .andExpect(status().isForbidden());
}

@Test
void recruiterShouldCreateJob() throws Exception {

        String token = loginAndGetToken("recruiter@test.com");

        String jobJson = """
        {
          "title": "Backend Dev",
          "description": "Spring Boot",
          "company": "TechCorp",
          "recruiterId": 1
        }
        """;

        mockMvc.perform(
        post("/jobs")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jobJson)
        )
        .andExpect(status().isOk());
}

@Test
void invalidJwtShouldBeRejected() throws Exception {

        mockMvc.perform(
        get("/applications")
        .header("Authorization", "Bearer invalid.token.value")
        )
        .andExpect(status().isUnauthorized());
}






