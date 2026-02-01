@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUserByEmail() {
        User user = new User();
        user.setName("John");
        user.setEmail("john@test.com");
        user.setPassword("encoded");
        user.setRole(Role.CANDIDATE);

        userRepository.save(user);

        Optional<User> found =
                userRepository.findByEmail("john@test.com");

        assertTrue(found.isPresent());
        assertEquals("John", found.get().getName());
    }
}
