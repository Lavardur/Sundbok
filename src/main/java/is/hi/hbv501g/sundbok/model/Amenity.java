package is.hi.hbv501g.sundbok.model;

import jakarta.persistence.*;

@Entity
@Table(name = "amenities")
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pool_id", nullable = false)
    private Facility facility;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    // Constructors
    public Amenity() {}

    public Amenity(Facility facility, String name, String type) {
        this.facility = facility;
        this.name = name;
        this.type = type;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Facility getFacility() { return facility; }
    public void setFacility(Facility facility) { this.facility = facility; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return "Amenity{id=" + id + ", name='" + name + "', type='" + type + "'}";
    }
}
