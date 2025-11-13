package is.hi.hbv501g.sundbok.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column()
    private Boolean isAdmin;



    // Relationships
    @JsonManagedReference("user-reviews")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @JsonManagedReference("user-checkins")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CheckIn> checkIns;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="user_favorite_facilities",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="facility_id"))
    private Set<Facility> favoriteFacilities = new HashSet<>();

    // Minimal “accepted friends only” self-relation (no pending state)
    @JsonIgnore
    @ManyToMany
    @JoinTable(name="user_friends",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="friend_id"))
    private Set<User> friends = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="user_subscriptions",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="facility_id"))
    private Set<Facility> subscriptions = new HashSet<>();

    // Constructors
    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(String name, String password, String email, Boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Boolean getIsAdmin() { return isAdmin; }
    public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

    public List<CheckIn> getCheckIns() { return checkIns; }
    public void setCheckIns(List<CheckIn> checkIns) { this.checkIns = checkIns; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }

    public Set<Facility> getFavoriteFacilities(){ return favoriteFacilities; }

    public Set<User> getFriends(){ return friends; }

    public Set<Facility> getSubscriptions(){ return subscriptions; }
}
