package is.hi.hbv501g.sundbok.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "notifications")
public class Notification {

    public enum Type {
        FRIEND_REVIEW,
        FRIEND_CHECKIN,
        FRIEND_FAVORITE,
        FACILITY_SCHEDULE_UPDATED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Who should see this notification
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private String message;

    // Optional: link to a facility
    private Long facilityId;

    // Optional: who triggered it
    private Long actorUserId;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private boolean read = false;

    public Notification() {}

    public Notification(User user, Type type, String message,
                        Long facilityId, Long actorUserId) {
        this.user = user;
        this.type = type;
        this.message = message;
        this.facilityId = facilityId;
        this.actorUserId = actorUserId;
        this.createdAt = Instant.now();
        this.read = false;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getFacilityId() { return facilityId; }
    public void setFacilityId(Long facilityId) { this.facilityId = facilityId; }

    public Long getActorUserId() { return actorUserId; }
    public void setActorUserId(Long actorUserId) { this.actorUserId = actorUserId; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
}
