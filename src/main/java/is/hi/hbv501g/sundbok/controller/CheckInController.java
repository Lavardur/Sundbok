package is.hi.hbv501g.sundbok.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import is.hi.hbv501g.sundbok.model.CheckIn;
import java.util.List;
import is.hi.hbv501g.sundbok.service.CheckInService;

@RestController
@RequestMapping("/api/checkins")
public class CheckInController {

    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    // GET /api/checkins - Get all check-ins
    @GetMapping
    public ResponseEntity<Iterable<CheckIn>> getAllCheckIns() {
        return ResponseEntity.ok(checkInService.getAllCheckIns());
    }

    // GET /api/checkins/{id} - Get check-in by ID
    @GetMapping("/{id}")
    public ResponseEntity<CheckIn> getCheckInById(@PathVariable Long id) {
        return checkInService.getCheckInById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/checkins/user/{userId} - Get user's check-ins
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CheckIn>> getUserCheckIns(@PathVariable Long userId) {
        return ResponseEntity.ok(checkInService.getUserCheckIns(userId));
    }

    // GET /api/checkins/facility/{facilityId} - Get facility's check-ins
    @GetMapping("/facility/{facilityId}")
    public ResponseEntity<List<CheckIn>> getFacilityCheckIns(@PathVariable Long facilityId) {
        return ResponseEntity.ok(checkInService.getFacilityCheckIns(facilityId));
    }

    // POST /api/checkins - Create new check-in
    @PostMapping
    public ResponseEntity<CheckIn> createCheckIn(@RequestParam Long userId, @RequestParam Long facilityId) {
        try {
            CheckIn created = checkInService.checkIn(userId, facilityId);
            return ResponseEntity.status(201).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /api/checkins/{id} - Delete check-in
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckIn(@PathVariable Long id) {
        checkInService.deleteCheckIn(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/checkins/visited - Check if user visited facility
    @GetMapping("/visited")
    public ResponseEntity<Boolean> hasUserVisited(@RequestParam Long userId, @RequestParam Long facilityId) {
        return ResponseEntity.ok(checkInService.hasUserVisited(userId, facilityId));
    }
}
