package is.hi.hbv501g.sundbok.repository;
import is.hi.hbv501g.sundbok.model.Review;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findByFacilityId(Long facilityId);
    List<Review> findByUserId(Long userId);

}