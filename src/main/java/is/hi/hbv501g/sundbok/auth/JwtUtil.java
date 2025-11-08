package is.hi.hbv501g.sundbok.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    private final Key key;
    private final long expiryMs;

    public JwtUtil(
            @Value("${app.jwt.secret:change-this-32-bytes-minimum-secret!!!}") String secret,
            @Value("${app.jwt.ttl-ms:86400000}") long expiryMs // 24h
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiryMs = expiryMs;
    }

    public String generate(String name, boolean isAdmin) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(name)
                .addClaims(Map.of("admin", isAdmin))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiryMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}
