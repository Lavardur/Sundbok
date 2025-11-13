package is.hi.hbv501g.sundbok.auth;

import is.hi.hbv501g.sundbok.model.User;
import is.hi.hbv501g.sundbok.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService users;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    public AuthController(UserService users, PasswordEncoder encoder, JwtUtil jwt) {
        this.users = users; this.encoder = encoder; this.jwt = jwt;
    }

    public static record LoginRequest(String name, String password) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest body, HttpServletResponse res) {
        var opt = users.getUserByName(body.name());
        if (opt.isEmpty()) return ResponseEntity.status(401).body(Map.of("error","Invalid credentials"));
        User u = opt.get();

        if (u.getPassword() == null || !encoder.matches(body.password(), u.getPassword()))
            return ResponseEntity.status(401).body(Map.of("error","Invalid credentials"));

        String token = jwt.generate(u.getId(), u.getName(), Boolean.TRUE.equals(u.getIsAdmin()));

        Cookie c = new Cookie("AUTH", token);
        c.setHttpOnly(true);
        c.setPath("/");
        c.setMaxAge((int) Duration.ofDays(7).getSeconds());
        res.addCookie(c);

        return ResponseEntity.ok(Map.of("token", token, "name", u.getName(),"id", u.getId(), "isAdmin", u.getIsAdmin()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse res) {
        Cookie c = new Cookie("AUTH", "");
        c.setHttpOnly(true);
        c.setPath("/");
        c.setMaxAge(0);
        res.addCookie(c);
        return ResponseEntity.ok(Map.of("ok", true));
    }
}
