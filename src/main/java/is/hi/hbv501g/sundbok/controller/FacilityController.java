package is.hi.hbv501g.sundbok.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import is.hi.hbv501g.sundbok.repository.FacilityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import is.hi.hbv501g.sundbok.model.Facility;
import is.hi.hbv501g.sundbok.service.FacilityService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/facilities")
public class FacilityController {

    private final FacilityService facilityService;
    private final FacilityRepository facilityRepository;

    public FacilityController(FacilityService facilityService, FacilityRepository facilityRepository) {
        this.facilityService = facilityService;
        this.facilityRepository = facilityRepository;
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

    @GetMapping
    public ResponseEntity<List<Facility>> list(
            @RequestParam Optional<Double> latitude,
            @RequestParam Optional<Double> longitude,
            @RequestParam(name = "radius") Optional<Double> radius,
            @RequestParam Optional<String> search
    ) {
        System.out.println("params -> lat=" + latitude + ", lon=" + longitude + ", radius=" + radius + ", search=" + search);

        if (search.isPresent() && !search.get().isBlank()) {
            return ResponseEntity.ok(facilityService.searchByName(search.get()));
        }
        if (latitude.isPresent() && longitude.isPresent() && radius.isPresent()) {
            return ResponseEntity.ok(facilityService.findNearby(latitude.get(), longitude.get(), radius.get()));
        }
        return ResponseEntity.ok((List<Facility>) facilityService.getAll());
    }


    // GET /pools/{id}/availability   -> returns just fjoldi
    @GetMapping("/{id}/availability")
    public ResponseEntity<?> getAvailability(@PathVariable Long id) {
        return facilityRepository.findById(id)
                .map(f -> ResponseEntity.ok(Map.of(
                        "poolId", f.getId(),
                        "name", f.getName(),
                        "fjoldi", f.getFjoldi(),
                        "updatedAt", f.getFjoldiUpdatedAt()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    // (Optional) POST /admin/refresh-fjoldi  -> manual refresh trigger
    @PostMapping("/admin/refresh-fjoldi")
    public ResponseEntity<?> refreshFjoldi() throws JsonProcessingException {
        facilityService.refreshFjoldi();
        return ResponseEntity.ok(Map.of("ok", true));
    }
    @GetMapping("/{facilityId}/schedule")
    public ResponseEntity<List<Facility.ScheduleRow>> getSchedule(@PathVariable Long facilityId){
        return ResponseEntity.ok(facilityService.getSchedule(facilityId));
    }

    @PutMapping("/{facilityId}/schedule")
    public ResponseEntity<List<Facility.ScheduleRow>> putSchedule(
            @PathVariable Long facilityId,
            @RequestBody List<Facility.ScheduleRow> rows
    ) {
        return ResponseEntity.ok(facilityService.replaceSchedule(facilityId, rows));
    }



}
