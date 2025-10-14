package is.hi.hbv501g.sundbok.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import is.hi.hbv501g.sundbok.model.CheckIn;
import is.hi.hbv501g.sundbok.service.CheckInService;
import java.util.List;

@RestController
@RequestMapping("/api/checkins")
public class CheckInController {

    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    public record CheckInDto(Long id, Long userId, Long facilityId, java.time.LocalDateTime visitedAt) {
        public static CheckInDto from(CheckIn c) {
            return new CheckInDto(
                    c.getId(),
                    c.getUser().getId(),
                    c.getFacility().getId(),
                    c.getVisitedAt()
            );
        }
    }

    // GET /api/checkins
    @GetMapping
    public ResponseEntity<List<CheckInDto>> getAllCheckIns() {
        var list = new java.util.ArrayList<CheckInDto>();
        checkInService.getAllCheckIns().forEach(c -> list.add(CheckInDto.from(c)));
        return ResponseEntity.ok(list);
    }

    // GET /api/checkins/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CheckInDto> getCheckInById(@PathVariable Long id) {
        return checkInService.getCheckInById(id)
                .map(c -> ResponseEntity.ok(CheckInDto.from(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/checkins/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CheckInDto>> getUserCheckIns(@PathVariable Long userId) {
        return ResponseEntity.ok(
                checkInService.getUserCheckIns(userId).stream()
                        .map(CheckInDto::from)
                        .toList()
        );
    }

    // GET /api/checkins/facility/{facilityId}
    @GetMapping("/facility/{facilityId}")
    public ResponseEntity<List<CheckInDto>> getFacilityCheckIns(@PathVariable Long facilityId) {
        return ResponseEntity.ok(
                checkInService.getFacilityCheckIns(facilityId).stream()
                        .map(CheckInDto::from)
                        .toList()
        );
    }

    // POST /api/checkins
    @PostMapping
    public ResponseEntity<CheckInDto> createCheckIn(@RequestParam Long userId,
                                                    @RequestParam Long facilityId) {
        CheckIn saved = checkInService.checkIn(userId, facilityId);
        return ResponseEntity
                .created(java.net.URI.create("/api/checkins/" + saved.getId()))
                .body(CheckInDto.from(saved));
    }

    // DELETE /api/checkins
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckIn(@PathVariable Long id) {
        checkInService.deleteCheckIn(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/checkins/visited?userId={userid}&facilityid={id}
    @GetMapping("/visited")
    public ResponseEntity<Boolean> hasUserVisited(@RequestParam Long userId,
                                                  @RequestParam Long facilityId) {
        return ResponseEntity.ok(checkInService.hasUserVisited(userId, facilityId));
    }
}