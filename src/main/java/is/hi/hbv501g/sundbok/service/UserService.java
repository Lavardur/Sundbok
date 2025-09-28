package is.hi.hbv501g.sundbok.service;

import is.hi.hbv501g.sundbok.model.User;
import is.hi.hbv501g.sundbok.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE - Register/Create user
    public User createUser(User user) {
        // Add validation logic here
        if (userRepository.existsByName(user.getName())) {
            throw new RuntimeException("User with name '" + user.getName() + "' already exists");
        }
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
                user.setName(updatedUser.getName());
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
}
