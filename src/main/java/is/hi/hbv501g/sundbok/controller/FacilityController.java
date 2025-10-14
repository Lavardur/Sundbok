package is.hi.hbv501g.sundbok.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import is.hi.hbv501g.sundbok.model.Facility;
import is.hi.hbv501g.sundbok.service.FacilityService;

@RestController
@RequestMapping("/api/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    // GET /api/facilities - Get all facilities
    @GetMapping
    public ResponseEntity<Iterable<Facility>> getAllFacilities() {
        return ResponseEntity.ok(facilityService.getAllFacilities());
    }

    // GET /api/facilities/{id} - Get facility by ID
    @GetMapping("/{id}")
    public ResponseEntity<Facility> getFacilityById(@PathVariable Long id) {
        return facilityService.getFacilityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/facilities - Create new facility
    @PostMapping
    public ResponseEntity<Facility> createFacility(@RequestBody Facility facility) {
        Facility saved = facilityService.createFacility(facility);
        return ResponseEntity.status(201).body(saved);
    }

    // PUT /api/facilities/{id} - Update facility
    @PutMapping("/{id}")
    public ResponseEntity<Facility> updateFacility(@PathVariable Long id, @RequestBody Facility facility) {
        try {
            Facility updated = facilityService.updateFacility(id, facility);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/facilities/{id} - Delete facility
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        facilityService.deleteFacility(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/facilities/count - Get facility count
    @GetMapping("/count")
    public ResponseEntity<Long> getFacilityCount() {
        return ResponseEntity.ok(facilityService.getFacilityCount());
    }

    // GET /api/facilities/exists/{id} - Check if facility exists
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> facilityExists(@PathVariable Long id) {
        return ResponseEntity.ok(facilityService.facilityExists(id));
    }
}
