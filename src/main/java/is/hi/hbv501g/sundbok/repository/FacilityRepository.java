package is.hi.hbv501g.sundbok.repository;

import is.hi.hbv501g.sundbok.model.Facility;
import org.springframework.data.repository.CrudRepository;

public interface FacilityRepository extends CrudRepository<Facility, Long> {
    // You can add custom query methods here if needed
}