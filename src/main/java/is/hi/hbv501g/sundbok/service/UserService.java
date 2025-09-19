package is.hi.hbv501g.sundbok.service;

import is.hi.hbv501g.sundbok.model.User;
import is.hi.hbv501g.sundbok.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> get() {
        return userRepository.findAll();
    }
    public void processUser(User user) {
        System.out.println("Registering the user"+user);
        userRepository.save(user);

    }
}
