package is.hi.hbv501g.sundbok.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import is.hi.hbv501g.sundbok.model.CheckIn;
import java.util.List;

@RestController
@RequestMapping("/api/checkins")
public class CheckInController {

    // GET /api/checkins - Get all check-ins
    @GetMapping
    public ResponseEntity<Iterable<CheckIn>> getAllCheckIns() {
        // TODO: Implementation
        return null;
    }

    // GET /api/checkins/{id} - Get check-in by ID
    @GetMapping("/{id}")
    public ResponseEntity<CheckIn> getCheckInById(@PathVariable Long id) {
        // TODO: Implementation
        return null;
    }

    // GET /api/checkins/user/{userId} - Get user's check-ins
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CheckIn>> getUserCheckIns(@PathVariable Long userId) {
        // TODO: Implementation
        return null;
    }

    // GET /api/checkins/facility/{facilityId} - Get facility's check-ins
    @GetMapping("/facility/{facilityId}")
    public ResponseEntity<List<CheckIn>> getFacilityCheckIns(@PathVariable Long facilityId) {
        // TODO: Implementation
        return null;
    }

    // POST /api/checkins - Create new check-in
    @PostMapping
    public ResponseEntity<CheckIn> createCheckIn(@RequestParam Long userId, @RequestParam Long facilityId) {
        // TODO: Implementation
        return null;
    }

    // DELETE /api/checkins/{id} - Delete check-in
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckIn(@PathVariable Long id) {
        // TODO: Implementation
        return null;
    }

    // GET /api/checkins/visited - Check if user visited facility
    @GetMapping("/visited")
    public ResponseEntity<Boolean> hasUserVisited(@RequestParam Long userId, @RequestParam Long facilityId) {
        // TODO: Implementation
        return null;
    }
}
