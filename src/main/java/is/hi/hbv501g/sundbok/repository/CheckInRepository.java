package is.hi.hbv501g.sundbok.repository;

import is.hi.hbv501g.sundbok.model.CheckIn;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface CheckInRepository extends CrudRepository<CheckIn, Long> {
    
    // Find user's check-ins
    List<CheckIn> findByUserIdOrderByVisitedAtDesc(Long userId);
    
    // Find facility's check-ins
    List<CheckIn> findByFacilityIdOrderByVisitedAtDesc(Long facilityId);
    
    // Check if user visited facility
    boolean existsByUserIdAndFacilityId(Long userId, Long facilityId);
    
    // Find specific check-in
    Optional<CheckIn> findByUserIdAndFacilityId(Long userId, Long facilityId);
    
    // Count visits
    long countByUserIdAndFacilityId(Long userId, Long facilityId);
}