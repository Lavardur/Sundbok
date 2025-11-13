package is.hi.hbv501g.sundbok.service;

import is.hi.hbv501g.sundbok.model.Amenity;
import is.hi.hbv501g.sundbok.repository.AmenityRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AmenityService {

    private final AmenityRepository amenityRepository;

    public AmenityService(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    // CREATE - Add new amenity
    public Amenity createAmenity(Amenity amenity) {
        return amenityRepository.save(amenity);
    }

    // READ - Get all amenities
    public Iterable<Amenity> getAllAmenities() {
        return amenityRepository.findAll();
    }

    // READ - Get amenity by ID
    public Optional<Amenity> getAmenityById(Long id) {
        return amenityRepository.findById(id);
    }

    // READ - Get amenities by facility
    public Iterable<Amenity> getAmenitiesByFacility(Long facilityId) {
        return amenityRepository.findByFacilityId(facilityId);
    }

    // UPDATE - Update amenity
    public Amenity updateAmenity(Long id, Amenity updatedAmenity) {
        return amenityRepository.findById(id)
            .map(amenity -> {
                amenity.setName(updatedAmenity.getName());
                amenity.setType(updatedAmenity.getType());
                amenity.setFacility(updatedAmenity.getFacility());
                return amenityRepository.save(amenity);
            })
            .orElseThrow(() -> new RuntimeException("Amenity not found with id: " + id));
    }

    // DELETE - Delete amenity
    public void deleteAmenity(Long id) {
        amenityRepository.deleteById(id);
    }

    // EXISTS
    public boolean amenityExists(Long id) {
        return amenityRepository.existsById(id);
    }

    // COUNT
    public long getAmenityCount() {
        return amenityRepository.count();
    }
}
