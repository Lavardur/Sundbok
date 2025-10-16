package is.hi.hbv501g.sundbok.repository;

import is.hi.hbv501g.sundbok.model.Amenity;
import org.springframework.data.repository.CrudRepository;

public interface AmenityRepository extends CrudRepository<Amenity, Long> {
    // find amenities by facility id
    Iterable<Amenity> findByFacilityId(Long facilityId);
}
