package is.hi.hbv501g.sundbok.controller;

import is.hi.hbv501g.sundbok.auth.UserPrincipal;
import is.hi.hbv501g.sundbok.model.Facility;
import is.hi.hbv501g.sundbok.model.User;
import is.hi.hbv501g.sundbok.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

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

    private boolean isSelf(Long userId, org.springframework.security.core.Authentication auth) {
        if (auth == null || auth.getPrincipal() == null) return false;

        var principal = (UserPrincipal) auth.getPrincipal();
        return principal.id().equals(userId);
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
    // GET my favorites
    @GetMapping("/users/{userId}/favorites")
    public ResponseEntity<Set<Facility>> favorites(@PathVariable Long userId, org.springframework.security.core.Authentication auth){
        if (!isSelf(userId, auth)) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(userService.getUserById(userId).orElseThrow().getFavoriteFacilities());
    }

    // POST add favorite
    @PostMapping("/users/{userId}/favorites/{facilityId}")
    public ResponseEntity<Set<Facility>> addFav(@PathVariable Long userId, @PathVariable Long facilityId, org.springframework.security.core.Authentication auth){
        if (!isSelf(userId, auth)) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(userService.addFavorite(userId, facilityId));
    }

    // DELETE remove favorite
    @DeleteMapping("/users/{userId}/favorites/{facilityId}")
    public ResponseEntity<Set<Facility>> rmFav(@PathVariable Long userId, @PathVariable Long facilityId, org.springframework.security.core.Authentication auth){
        if (!isSelf(userId, auth)) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(userService.removeFavorite(userId, facilityId));
    }

    // GET my friends
    @GetMapping("/users/{userId}/friends")
    public ResponseEntity<Set<User>> friends(@PathVariable Long userId, org.springframework.security.core.Authentication auth){
        if (!isSelf(userId, auth)) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(userService.getUserById(userId).orElseThrow().getFriends());
    }

    // POST befriend (mutual, no requests)
    @PostMapping("/users/{userId}/friends/{otherId}")
    public ResponseEntity<Set<User>> befriend(@PathVariable Long userId, @PathVariable Long otherId, org.springframework.security.core.Authentication auth){
        if (!isSelf(userId, auth)) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(userService.addFriendship(userId, otherId));
    }

    // DELETE unfriend
    @DeleteMapping("/users/{userId}/friends/{otherId}")
    public ResponseEntity<Void> unfriend(@PathVariable Long userId, @PathVariable Long otherId, org.springframework.security.core.Authentication auth){
        if (!isSelf(userId, auth)) return ResponseEntity.status(403).build();
        userService.removeFriendship(userId, otherId);
        return ResponseEntity.noContent().build();
    }

    // GET my subscriptions
    @GetMapping("/users/{userId}/subscriptions")
    public ResponseEntity<Set<Facility>> subs(@PathVariable Long userId, org.springframework.security.core.Authentication auth){
        if (!isSelf(userId, auth)) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(userService.getUserById(userId).orElseThrow().getSubscriptions());
    }

    // POST subscribe
    @PostMapping("/users/{userId}/subscriptions/{facilityId}")
    public ResponseEntity<Set<Facility>> sub(@PathVariable Long userId, @PathVariable Long facilityId, org.springframework.security.core.Authentication auth){
        if (!isSelf(userId, auth)) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(userService.subscribe(userId, facilityId));
    }

    // DELETE unsubscribe
    @DeleteMapping("/users/{userId}/subscriptions/{facilityId}")
    public ResponseEntity<Set<Facility>> unsub(@PathVariable Long userId, @PathVariable Long facilityId, org.springframework.security.core.Authentication auth){
        if (!isSelf(userId, auth)) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(userService.unsubscribe(userId, facilityId));
    }

}
