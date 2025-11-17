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

                var optUser = users.getUserById(id);
                if (optUser.isPresent()) {
                    var u = optUser.get();
                    String username = u.getName(); // always current name from DB
                    boolean isAdmin = Boolean.TRUE.equals(u.getIsAdmin());

                    var principal = new UserPrincipal(u.getId(), username, isAdmin);

                    List<SimpleGrantedAuthority> auths =
                            isAdmin ? List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                                    : List.of(new SimpleGrantedAuthority("ROLE_USER"));

                    var auth = new UsernamePasswordAuthenticationToken(principal, null, auths);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception ignored) {
                // invalid token -> no auth, same as before
            }
        }


        chain.doFilter(req, res);
    }
}
