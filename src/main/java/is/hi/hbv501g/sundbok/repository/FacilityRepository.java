package is.hi.hbv501g.sundbok.repository;

import  is.hi.hbv501g.sundbok.model.Facility;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FacilityRepository extends CrudRepository<Facility, Long> {
    List<Facility> findByNameContainingIgnoreCase(String q);
    @Query(value = """
        SELECT * FROM pools f
        WHERE f.latitude  IS NOT NULL
          AND f.longitude IS NOT NULL
          AND (
            6371 * acos(
              cos(radians(:lat)) * cos(radians(f.latitude)) *
              cos(radians(f.longitude) - radians(:lng)) +
              sin(radians(:lat)) * sin(radians(f.latitude))
            )
          ) <= :radius
        ORDER BY (
            6371 * acos(
              cos(radians(:lat)) * cos(radians(f.latitude)) *
              cos(radians(f.longitude) - radians(:lng)) +
              sin(radians(:lat)) * sin(radians(f.latitude))
            )
          ) ASC
        """,
            nativeQuery = true)
    List<Facility> findWithinRadius(@Param("lat") double lat,
                                    @Param("lng") double lng,
                                    @Param("radius") double radius);
}