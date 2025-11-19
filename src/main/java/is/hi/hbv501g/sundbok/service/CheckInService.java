package is.hi.hbv501g.sundbok.service;

import is.hi.hbv501g.sundbok.model.CheckIn;
import is.hi.hbv501g.sundbok.model.Facility;
import is.hi.hbv501g.sundbok.model.Notification;
import is.hi.hbv501g.sundbok.model.User;
import is.hi.hbv501g.sundbok.repository.CheckInRepository;
import is.hi.hbv501g.sundbok.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final UserService userService;
    private final FacilityService facilityService;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public CheckInService(CheckInRepository checkInRepository, UserService userService, FacilityService facilityService, UserRepository userRepository,
                          NotificationService notificationService) {
        this.checkInRepository = checkInRepository;
        this.userService = userService;
        this.facilityService = facilityService;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    // CREATE - Check in to a facility
    public CheckIn checkIn(Long userId, Long facilityId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Facility facility = facilityService.getFacilityById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found"));

        CheckIn checkIn = new CheckIn(user, facility);
        CheckIn saved = checkInRepository.save(checkIn);

        //Notify followers about the check-in
        var followers = new HashSet<>(userRepository.findFollowersOf(user.getId()));
        if (!followers.isEmpty()) {
            String msg = user.getName() + " checked in at " + facility.getName();
            notificationService.createForUsers(
                    followers,
                    Notification.Type.FRIEND_CHECKIN,
                    msg,
                    facility.getId(),
                    user.getId()
            );
        }
        return saved;
    }


    // READ - Get all check-ins
    public Iterable<CheckIn> getAllCheckIns() {
        return checkInRepository.findAll();
    }

    // READ - Get check-in by ID
    public Optional<CheckIn> getCheckInById(Long id) {
        return checkInRepository.findById(id);
    }

    // READ - Get user's check-in history
    public List<CheckIn> getUserCheckIns(Long userId) {
        return checkInRepository.findByUserIdOrderByVisitedAtDesc(userId);
    }

    // READ - Get facility's check-ins
    public List<CheckIn> getFacilityCheckIns(Long facilityId) {
        return checkInRepository.findByFacilityIdOrderByVisitedAtDesc(facilityId);
    }

    // DELETE - Remove check-in
    public void deleteCheckIn(Long id) {
        checkInRepository.deleteById(id);
    }

    // UTILITY - Check if user has visited a facility
    public boolean hasUserVisited(Long userId, Long facilityId) {
        return checkInRepository.existsByUserIdAndFacilityId(userId, facilityId);
    }
}
