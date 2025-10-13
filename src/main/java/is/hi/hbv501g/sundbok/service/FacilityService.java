package is.hi.hbv501g.sundbok.service;

import is.hi.hbv501g.sundbok.model.Facility;
import is.hi.hbv501g.sundbok.repository.FacilityRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public FacilityService(FacilityRepository facilityRepository) {

        this.facilityRepository = facilityRepository;
    }

    // CREATE - Add new facility
    public Facility createFacility(Facility facility) {

        return facilityRepository.save(facility);
    }

    // READ - Get all facilities
    public Iterable<Facility> getAllFacilities() {

        return facilityRepository.findAll();
    }

    // READ - Get facility by ID
    public Optional<Facility> getFacilityById(Long id) {

        return facilityRepository.findById(id);
    }

    // UPDATE - Update facility
    public Facility updateFacility(Long id, Facility updatedFacility) {
        return facilityRepository.findById(id)
            .map(facility -> {
                facility.setName(updatedFacility.getName());
                facility.setAddress(updatedFacility.getAddress());
                return facilityRepository.save(facility);
            })
            .orElseThrow(() -> new RuntimeException("Facility not found with id: " + id));
    }

    // DELETE - Delete facility
    public void deleteFacility(Long id) {

        facilityRepository.deleteById(id);
    }

    // EXISTS
    public boolean facilityExists(Long id) {
        return facilityRepository.existsById(id);
    }

    // COUNT
    public long getFacilityCount() {
        return facilityRepository.count();
    }
}
