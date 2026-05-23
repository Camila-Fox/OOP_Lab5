package extravagant.cafe.security;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        if ("admin".equals(username) && "admin".equals(password)) {
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", jwtUtil.generateAccessToken(username));
            tokens.put("refreshToken", jwtUtil.generateRefreshToken(username));
            return tokens;
        }
        throw new RuntimeException("Invalid credentials");
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestParam String refreshToken) {
        if (jwtUtil.validateToken(refreshToken)) {
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", jwtUtil.generateAccessToken("admin"));
            return tokens;
        }
        throw new RuntimeException("Invalid refresh token");
    }
}