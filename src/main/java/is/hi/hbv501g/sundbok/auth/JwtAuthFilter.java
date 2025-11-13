package is.hi.hbv501g.sundbok.auth;

import is.hi.hbv501g.sundbok.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwt;
    private final UserService users;

    public JwtAuthFilter(JwtUtil jwt, UserService users) {
        this.jwt = jwt;
        this.users = users;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String token = null;

        String h = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (h != null && h.startsWith("Bearer ")) token = h.substring(7);

        if (token == null && req.getCookies() != null) {
            token = Arrays.stream(req.getCookies())
                    .filter(c -> "AUTH".equals(c.getName()))
                    .map(Cookie::getValue)
                    .findFirst().orElse(null);
        }

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                var claims = jwt.parse(token).getBody();
                Long id = claims.get("id", Integer.class).longValue();
                String username = claims.getSubject();
                boolean isAdmin = claims.get("admin", Boolean.class);

                var principal = new UserPrincipal(id, username, isAdmin);

                if (users.getUserByName(username).isPresent()) {
                    List<SimpleGrantedAuthority> auths =
                            isAdmin ? List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                                    : List.of(new SimpleGrantedAuthority("ROLE_USER"));

                    var auth = new UsernamePasswordAuthenticationToken(principal, null, auths);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception ignored) {}
        }

        chain.doFilter(req, res);
    }
}
