package is.hi.hbv501g.sundbok.repository;

import is.hi.hbv501g.sundbok.model.User;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    // CrudRepository provides these methods automatically:
    // save(User user)
    // findById(Long id)
    // findAll()
    // deleteById(Long id)
    // delete(User user)
    // count()
    // existsById(Long id)

    // Custom query methods
    Optional<User> findByName(String name);
    boolean existsByName(String name);
    void deleteByName(String name);
}
