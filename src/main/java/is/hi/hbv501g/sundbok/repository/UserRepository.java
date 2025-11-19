package is.hi.hbv501g.sundbok.repository;

import is.hi.hbv501g.sundbok.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    @Query("select u from User u join u.friends f where f.id = :followedId")
    List<User> findFollowersOf(@Param("followedId") Long followedId);

    @Query("select u from User u join u.subscriptions s where s.id = :facilityId")
    List<User> findSubscribersOfFacility(@Param("facilityId") Long facilityId);
}
