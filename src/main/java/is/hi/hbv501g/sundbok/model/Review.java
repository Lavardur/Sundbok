package is.hi.hbv501g.sundbok.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user-reviews")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pool_id", nullable = false)
    @JsonBackReference("facility-reviews")
    private Facility facility;

    @Column(nullable = false)
    private Integer rating; // 1-5 penguin rating

    @Column(columnDefinition = "TEXT")
    private String comment;

    // Constructors
    public Review() {}

    public Review(User user, Facility facility, Integer rating, String comment) {
        this.user = user;
        this.facility = facility;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Facility getFacility() { return facility; }
    public void setFacility(Facility facility) { this.facility = facility; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    @Override
    public String toString() {
        return "Review{id=" + id + ", rating=" + rating + ", comment='" + comment + "'}";
    }
}
