package is.hi.hbv501g.sundbok.repository;

import is.hi.hbv501g.sundbok.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
}
