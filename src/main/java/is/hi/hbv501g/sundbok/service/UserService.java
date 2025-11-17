package is.hi.hbv501g.sundbok.service;

import is.hi.hbv501g.sundbok.model.Facility;
import is.hi.hbv501g.sundbok.model.User;
import is.hi.hbv501g.sundbok.repository.FacilityRepository;
import is.hi.hbv501g.sundbok.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FacilityRepository facilityRepository;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, FacilityRepository facilityRepository) {
        this.userRepository = userRepository;
        this.facilityRepository = facilityRepository;
    }

    private boolean isBCrypt(String s) {
        return s != null && s.matches("^\\$2[aby]?\\$\\d\\d\\$.*");
    }

    private String ensureHashed(String rawOrHash) {
        if (rawOrHash == null || rawOrHash.isBlank()) return rawOrHash;
        return isBCrypt(rawOrHash) ? rawOrHash : encoder.encode(rawOrHash);
    }

    // CREATE - Register/Create user
    public User createUser(User user) {
        // Add validation logic here
        if (userRepository.existsByName(user.getName())) {
            throw new RuntimeException("User with name '" + user.getName() + "' already exists");
        }
        user.setPassword(ensureHashed(user.getPassword()));
        if (user.getIsAdmin() == null) user.setIsAdmin(false);
        System.out.println("Registering user: " + user);
        return userRepository.save(user);
    }

    // READ - Get all users
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    // READ - Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // READ - Get user by name
    public Optional<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }

    // UPDATE - Update existing user
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    if (updatedUser.getName() != null)   user.setName(updatedUser.getName());
                    if (updatedUser.getEmail() != null)  user.setEmail(updatedUser.getEmail());
                    if (updatedUser.getIsAdmin() != null) user.setIsAdmin(updatedUser.getIsAdmin());

                    // Only change password if a new value is supplied
                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
                        user.setPassword(ensureHashed(updatedUser.getPassword()));
                    }
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // DELETE - Delete user by ID
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    // DELETE - Delete user by name
    public void deleteUserByName(String name) {
        if (!userRepository.existsByName(name)) {
            throw new RuntimeException("User not found with name: " + name);
        }
        userRepository.deleteByName(name);
    }

    // UTILITY - Check if user exists
    public boolean userExists(Long id) {
        return userRepository.existsById(id);
    }

    public boolean userExistsByName(String name) {
        return userRepository.existsByName(name);
    }

    // UTILITY - Count total users
    public long getUserCount() {
        return userRepository.count();
    }

    // Keep your existing method for backward compatibility
    @Deprecated
    public void processUser(User user) {
        createUser(user);
    }

    public Iterable<User> get() {
        return getAllUsers();
    }
    @Transactional
    public Set<Facility> addFavorite(Long userId, Long facilityId){
        User u = userRepository.findById(userId).orElseThrow();
        Facility f = facilityRepository.findById(facilityId).orElseThrow();
        u.getFavoriteFacilities().add(f);
        return u.getFavoriteFacilities();
    }

    @Transactional
    public Set<Facility> removeFavorite(Long userId, Long facilityId){
        User u = userRepository.findById(userId).orElseThrow();
        Facility f = facilityRepository.findById(facilityId).orElseThrow();
        u.getFavoriteFacilities().remove(f);
        return u.getFavoriteFacilities();
    }

    @Transactional
    public Set<User> addFriendship(Long meId, Long otherId){
        if (meId.equals(otherId)) throw new RuntimeException("Cannot friend yourself");
        User me = userRepository.findById(meId).orElseThrow();
        User other = userRepository.findById(otherId).orElseThrow();
        me.getFriends().add(other);
        other.getFriends().add(me);           // keep it mutual
        return me.getFriends();
    }

    @Transactional
    public void removeFriendship(Long meId, Long otherId){
        User me = userRepository.findById(meId).orElseThrow();
        User other = userRepository.findById(otherId).orElseThrow();
        me.getFriends().remove(other);
        other.getFriends().remove(me);
    }

    @Transactional
    public Set<Facility> subscribe(Long userId, Long facilityId){
        User u = userRepository.findById(userId).orElseThrow();
        Facility f = facilityRepository.findById(facilityId).orElseThrow();
        u.getSubscriptions().add(f);
        return u.getSubscriptions();
    }

    @Transactional
    public Set<Facility> unsubscribe(Long userId, Long facilityId){
        User u = userRepository.findById(userId).orElseThrow();
        Facility f = facilityRepository.findById(facilityId).orElseThrow();
        u.getSubscriptions().remove(f);
        return u.getSubscriptions();
    }
    @Transactional
    public void updateProfilePicture(Long userId, byte[] image) {
        User u = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found with id: " + userId)
        );
        u.setProfilePicture(image);
    }

    @Transactional
    public void deleteProfilePicture(Long userId) {
        User u = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found with id: " + userId)
        );
        u.setProfilePicture(null);
    }

    public byte[] getProfilePicture(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found with id: " + userId)
        ).getProfilePicture();
    }


}
