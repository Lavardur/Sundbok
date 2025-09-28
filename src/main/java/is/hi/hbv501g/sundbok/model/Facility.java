package is.hi.hbv501g.sundbok.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "pools")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    // Relationships
    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    private List<Amenity> amenities;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    private List<CheckIn> checkIns;

    // Constructors
    public Facility() {}

    public Facility(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<Amenity> getAmenities() { return amenities; }
    public void setAmenities(List<Amenity> amenities) { this.amenities = amenities; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

    public List<CheckIn> getCheckIns() { return checkIns; }
    public void setCheckIns(List<CheckIn> checkIns) { this.checkIns = checkIns; }

    @Override
    public String toString() {
        return "Facility{id=" + id + ", name='" + name + "', address='" + address + "'}";
    }
}
