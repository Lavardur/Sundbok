package is.hi.hbv501g.sundbok.repository;

import is.hi.hbv501g.sundbok.model.Facility;
import is.hi.hbv501g.sundbok.model.Review;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface FacilityRepository extends CrudRepository<Facility, Long> {
    // You can add custom query methods here if needed
        List<Review> findByFacilityId(Long facilityId);
}