package is.hi.hbv501g.sundbok.controller;

import is.hi.hbv501g.sundbok.auth.UserPrincipal;
import is.hi.hbv501g.sundbok.model.Facility;
import is.hi.hbv501g.sundbok.model.User;
import is.hi.hbv501g.sundbok.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
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
    // GET /api/users/{id}/profile-picture  (public read)
    @GetMapping("/{id}/profile-picture")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long id) {
        try {
            byte[] data = userService.getProfilePicture(id);
            if (data == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                    .body(data);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /api/users/{id}/profile-picture  (user can only change their own)
    @PutMapping(path = "/{id}/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadProfilePicture(@PathVariable Long id,@RequestParam("file") MultipartFile file, org.springframework.security.core.Authentication auth) {
        if (!isSelf(id, auth)) {
            return ResponseEntity.status(403).build();
        }
        try {
            byte[] compressed = toCompressedJpeg(file, 600, 0.4f);
            userService.updateProfilePicture(id, compressed);
            return ResponseEntity.noContent().build();
        } catch (IOException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /api/users/{id}/profile-picture  (self only)
    @DeleteMapping("/{id}/profile-picture")
    public ResponseEntity<Void> deleteProfilePicture(@PathVariable Long id,org.springframework.security.core.Authentication auth) {
        if (!isSelf(id, auth)) {
            return ResponseEntity.status(403).build();
        }
        userService.deleteProfilePicture(id);
        return ResponseEntity.noContent().build();
    }

    private byte[] toCompressedJpeg(MultipartFile file, int maxSize, float quality) throws IOException {
        BufferedImage img = ImageIO.read(file.getInputStream());
        if (img == null) {
            throw new IllegalArgumentException("Uploaded file is not an image");
        }

        int w = img.getWidth();
        int h = img.getHeight();
        int max = Math.max(w, h);

        // scale down if needed
        if (max > maxSize) {
            double scale = (double) maxSize / max;
            int newW = (int) (w * scale);
            int newH = (int) (h * scale);

            Image scaled = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            BufferedImage resized = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = resized.createGraphics();
            g2.drawImage(scaled, 0, 0, null);
            g2.dispose();
            img = resized;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No JPEG writer available");
        }
        ImageWriter writer = writers.next();
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(baos)) {
            writer.setOutput(ios);
            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(quality); // 0.0–1.0 (lower = smaller)
            }
            writer.write(null, new IIOImage(img, null, null), param);
        } finally {
            writer.dispose();
        }
        return baos.toByteArray();
    }


}
