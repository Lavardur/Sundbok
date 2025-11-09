package is.hi.hbv501g.sundbok.controller;

import is.hi.hbv501g.sundbok.model.User;
import is.hi.hbv501g.sundbok.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/users - Get all users
    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET /api/users/{id} - Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/users/search?name=username - Get user by name
    @GetMapping("/search")
    public ResponseEntity<User> getUserByName(@RequestParam String name) {
        Optional<User> user = userService.getUserByName(name);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/users - Create new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user,
                                           org.springframework.security.core.Authentication auth) {
        // Only an existing admin may set isAdmin; otherwise false
        boolean admin = isAdmin(auth);
        if (!admin) user.setIsAdmin(false);
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    private static boolean isAdmin(org.springframework.security.core.Authentication auth) {
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
    private static String who(org.springframework.security.core.Authentication auth) {
        return auth != null ? String.valueOf(auth.getPrincipal()) : null;
    }

    // PUT /api/users/{id} - Update user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody User user,
                                           org.springframework.security.core.Authentication auth) {
        var target = userService.getUserById(id);
        if (target.isEmpty()) return ResponseEntity.notFound().build();

        boolean admin = isAdmin(auth);
        String me = who(auth);
        if (!admin && (me == null || !target.get().getName().equals(me))) {
            return ResponseEntity.status(403).build();
        }
        // non-admins cannot flip isAdmin
        if (!admin) user.setIsAdmin(null);

        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    // DELETE /api/users/{id} - Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id,
                                           org.springframework.security.core.Authentication auth) {
        var target = userService.getUserById(id);
        if (target.isEmpty()) return ResponseEntity.notFound().build();

        boolean admin = isAdmin(auth);
        String me = who(auth);
        if (!admin && (me == null || !target.get().getName().equals(me))) {
            return ResponseEntity.status(403).build();
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/users/count - Get user count
    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        return ResponseEntity.ok(userService.getUserCount());
    }

    // GET /api/users/exists/{id} - Check if user exists
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> userExists(@PathVariable Long id) {
        return ResponseEntity.ok(userService.userExists(id));
    }
}
