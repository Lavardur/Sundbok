package is.hi.hbv501g.sundbok.auth;

import is.hi.hbv501g.sundbok.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JwtUtil jwt, UserService users) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // auth
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                        // users
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()             // signup
                        .requestMatchers(HttpMethod.GET,  "/api/users/**").permitAll()          // public read
                        .requestMatchers(HttpMethod.PUT,  "/api/users/**").authenticated()      // owner/admin check in controller
                        .requestMatchers(HttpMethod.DELETE,"/api/users/**").authenticated()     // owner/admin check in controller

                        // facilities (admins only)
                        .requestMatchers(HttpMethod.POST,   "/api/facilities/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/api/facilities/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/facilities/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,    "/api/facilities/**").permitAll()

                        // amenities (admins only for changes)
                        .requestMatchers(HttpMethod.POST,   "/api/amenities/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/api/amenities/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/amenities/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,    "/api/amenities/**").permitAll()

                        // reviews & checkins (must be logged in)
                        .requestMatchers(HttpMethod.POST, "/api/reviews/**", "/api/checkins/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,  "/api/reviews/**", "/api/checkins/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/api/reviews/**","/api/checkins/**").authenticated()

                        .requestMatchers(HttpMethod.GET,"/api/users/*/favorites/**").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/users/*/favorites/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/api/users/*/favorites/**").authenticated()

                        .requestMatchers(HttpMethod.GET,"/api/users/*/friends/**").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/users/*/friends/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/api/users/*/friends/**").authenticated()

                        .requestMatchers(HttpMethod.GET,"/api/users/*/subscriptions/**").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/users/*/subscriptions/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/api/users/*/subscriptions/**").authenticated()
                        // everything else
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtAuthFilter(jwt, users), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
