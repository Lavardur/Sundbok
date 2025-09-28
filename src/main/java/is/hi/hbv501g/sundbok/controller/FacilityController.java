package is.hi.hbv501g.sundbok.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import is.hi.hbv501g.sundbok.model.Facility;

@RestController
@RequestMapping("/api/facilities")
public class FacilityController {

    // GET /api/facilities - Get all facilities
    @GetMapping
    public ResponseEntity<Iterable<Facility>> getAllFacilities() {
        // TODO: Implementation
        return null;
    }

    // GET /api/facilities/{id} - Get facility by ID
    @GetMapping("/{id}")
    public ResponseEntity<Facility> getFacilityById(@PathVariable Long id) {
        // TODO: Implementation
        return null;
    }

    // GET /api/facilities/search - Search facilities by name
    @GetMapping("/search")
    public ResponseEntity<Iterable<Facility>> searchFacilities(@RequestParam String name) {
        // TODO: Implementation
        return null;
    }

    // POST /api/facilities - Create new facility
    @PostMapping
    public ResponseEntity<Facility> createFacility(@RequestBody Facility facility) {
        // TODO: Implementation
        return null;
    }

    // PUT /api/facilities/{id} - Update facility
    @PutMapping("/{id}")
    public ResponseEntity<Facility> updateFacility(@PathVariable Long id, @RequestBody Facility facility) {
        // TODO: Implementation
        return null;
    }

    // DELETE /api/facilities/{id} - Delete facility
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        // TODO: Implementation
        return null;
    }

    // GET /api/facilities/count - Get facility count
    @GetMapping("/count")
    public ResponseEntity<Long> getFacilityCount() {
        // TODO: Implementation
        return null;
    }
}
