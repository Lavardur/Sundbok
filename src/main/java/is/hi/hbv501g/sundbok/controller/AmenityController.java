package is.hi.hbv501g.sundbok.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import is.hi.hbv501g.sundbok.model.Amenity;

@RestController
@RequestMapping("/api/amenities")
public class AmenityController {

    // GET /api/amenities - Get all amenities
    @GetMapping
    public ResponseEntity<Iterable<Amenity>> getAllAmenities() {
        // TODO: Implementation
        return null;
    }

    // GET /api/amenities/{id} - Get amenity by ID
    @GetMapping("/{id}")
    public ResponseEntity<Amenity> getAmenityById(@PathVariable Long id) {
        // TODO: Implementation
        return null;
    }

    // GET /api/amenities/facility/{facilityId} - Get amenities for facility
    @GetMapping("/facility/{facilityId}")
    public ResponseEntity<Iterable<Amenity>> getAmenitiesByFacility(@PathVariable Long facilityId) {
        // TODO: Implementation
        return null;
    }

    // POST /api/amenities - Create new amenity
    @PostMapping
    public ResponseEntity<Amenity> createAmenity(@RequestBody Amenity amenity) {
        // TODO: Implementation
        return null;
    }

    // PUT /api/amenities/{id} - Update amenity
    @PutMapping("/{id}")
    public ResponseEntity<Amenity> updateAmenity(@PathVariable Long id, @RequestBody Amenity amenity) {
        // TODO: Implementation
        return null;
    }

    // DELETE /api/amenities/{id} - Delete amenity
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmenity(@PathVariable Long id) {
        // TODO: Implementation
        return null;
    }
}
