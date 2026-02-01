@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;

    public AuthController(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        CustomUserDetails user =
                (CustomUserDetails) auth.getPrincipal();

        return JwtUtil.generateToken(
                user.getUsername(),
                user.getAuthorities().iterator().next().getAuthority()
        );
    }
}
