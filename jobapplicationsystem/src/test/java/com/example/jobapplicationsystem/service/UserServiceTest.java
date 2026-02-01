@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUserSuccessfully() {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("John");
        request.setEmail("john@test.com");
        request.setPassword("password123");
        request.setRole(Role.CANDIDATE);

        when(passwordEncoder.encode("password123"))
                .thenReturn("encodedPass");

        when(userRepository.save(any(User.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        User user = userService.createUser(request);

        assertNotNull(user);
        assertEquals("John", user.getName());
        assertEquals("john@test.com", user.getEmail());
        assertEquals("encodedPass", user.getPassword());
        assertEquals(Role.CANDIDATE, user.getRole());
    }
}
