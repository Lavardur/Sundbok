package is.hi.hbv501g.sundbok.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "checkins")
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user-checkins")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pool_id", nullable = false)
    @JsonBackReference("facility-checkins")
    private Facility facility;

    @Column(name = "visited_at")
    private LocalDateTime visitedAt;

    // Constructors
    public CheckIn() {
        this.visitedAt = LocalDateTime.now();
    }

    public CheckIn(User user, Facility facility) {
        this.user = user;
        this.facility = facility;
        this.visitedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Facility getFacility() { return facility; }
    public void setFacility(Facility facility) { this.facility = facility; }

    public LocalDateTime getVisitedAt() { return visitedAt; }
    public void setVisitedAt(LocalDateTime visitedAt) { this.visitedAt = visitedAt; }

    @Override
    public String toString() {
        return "CheckIn{id=" + id + ", visitedAt=" + visitedAt + "}";
    }
}
