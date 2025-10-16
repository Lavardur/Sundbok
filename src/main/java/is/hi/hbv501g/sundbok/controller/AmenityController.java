package is.hi.hbv501g.sundbok.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import is.hi.hbv501g.sundbok.model.Amenity;
import is.hi.hbv501g.sundbok.service.AmenityService;

@RestController
@RequestMapping("/api/amenities")
public class AmenityController {

    private final AmenityService amenityService;

    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    // GET /api/amenities - Get all amenities
    @GetMapping
    public ResponseEntity<Iterable<Amenity>> getAllAmenities() {
       return ResponseEntity.ok(amenityService.getAllAmenities());
    }

    // GET /api/amenities/{id} - Get amenity by ID
    @GetMapping("/{id}")
    public ResponseEntity<Amenity> getAmenityById(@PathVariable Long id) {
        return amenityService.getAmenityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/amenities/facility/{facilityId} - Get amenities for facility
    @GetMapping("/facility/{facilityId}")
    public ResponseEntity<Iterable<Amenity>> getAmenitiesByFacility(@PathVariable Long facilityId) {
        return ResponseEntity.ok(amenityService.getAmenitiesByFacility(facilityId));
    }

    // POST /api/amenities - Create new amenity
    @PostMapping
    public ResponseEntity<Amenity> createAmenity(@RequestBody Amenity amenity) {
        Amenity saved = amenityService.createAmenity(amenity);
        return ResponseEntity.status(201).body(saved);
    }

    // PUT /api/amenities/{id} - Update amenity
    @PutMapping("/{id}")
    public ResponseEntity<Amenity> updateAmenity(@PathVariable Long id, @RequestBody Amenity amenity) {
        try {
            Amenity updated = amenityService.updateAmenity(id, amenity);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/amenities/{id} - Delete amenity
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmenity(@PathVariable Long id) {
        amenityService.deleteAmenity(id);
        return ResponseEntity.noContent().build();
    }
}
