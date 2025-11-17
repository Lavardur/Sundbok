package is.hi.hbv501g.sundbok.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
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

    @Column private Double latitude;

    @Column private Double longitude;

    @Column private Integer fjoldi;

    private Instant fjoldiUpdatedAt;
    // Relationships
    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    @JsonManagedReference("facility-amenities")
    private List<Amenity> amenities;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    @JsonManagedReference("facility-reviews")
    private List<Review> reviews;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    @JsonManagedReference("facility-checkins")
    private List<CheckIn> checkIns;

    @ElementCollection
    @CollectionTable(name="facility_schedule", joinColumns=@JoinColumn(name="facility_id"))
    private List<ScheduleRow> schedule = new ArrayList<>();

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

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Integer getFjoldi() { return fjoldi; }
    public void setFjoldi(Integer fjoldi) { this.fjoldi = fjoldi; }

    public Instant getFjoldiUpdatedAt() { return fjoldiUpdatedAt; }
    public void setFjoldiUpdatedAt(Instant fjoldiUpdatedAt) { this.fjoldiUpdatedAt = fjoldiUpdatedAt; }

    public List<ScheduleRow> getSchedule(){ return schedule; }
    public void setSchedule(List<ScheduleRow> s){ this.schedule = s; }

    @Override
    public String toString() {
        return "Facility{id=" + id + ", name='" + name + "', address='" + address + "'}";
    }
    @Embeddable
    public static class ScheduleRow {
        // 1=Monday … 7=Sunday for simplicity
        private int dayOfWeek;
        private String open;   // "06:30"
        private String close;  // "22:00"
        private String notes;

        // getters/setters
        public ScheduleRow() {}

        public int getDayOfWeek() { return dayOfWeek; }
        public void setDayOfWeek(int dayOfWeek) { this.dayOfWeek = dayOfWeek; }

        public String getOpen() { return open; }
        public void setOpen(String open) { this.open = open; }

        public String getClose() { return close; }
        public void setClose(String close) { this.close = close; }

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }
    @Lob @Basic(fetch = FetchType.LAZY) @JsonIgnore
    private byte[] image1;

    @Lob @Basic(fetch = FetchType.LAZY) @JsonIgnore
    private byte[] image2;

    @Lob @Basic(fetch = FetchType.LAZY) @JsonIgnore
    private byte[] image3;

    @Lob @Basic(fetch = FetchType.LAZY) @JsonIgnore
    private byte[] image4;

    @Lob @Basic(fetch = FetchType.LAZY) @JsonIgnore
    private byte[] image5;

    @Lob @Basic(fetch = FetchType.LAZY) @JsonIgnore
    private byte[] image6;

    @Lob @Basic(fetch = FetchType.LAZY) @JsonIgnore
    private byte[] image7;

    @Lob @Basic(fetch = FetchType.LAZY) @JsonIgnore
    private byte[] image8;

    @Lob @Basic(fetch = FetchType.LAZY) @JsonIgnore
    private byte[] image9;

    @Lob @Basic(fetch = FetchType.LAZY) @JsonIgnore
    private byte[] image10;
    public byte[] getImage(int index) {
        return switch (index) {
            case 1 -> image1;
            case 2 -> image2;
            case 3 -> image3;
            case 4 -> image4;
            case 5 -> image5;
            case 6 -> image6;
            case 7 -> image7;
            case 8 -> image8;
            case 9 -> image9;
            case 10 -> image10;
            default -> throw new IllegalArgumentException("Image index must be 1–10");
        };
    }

    public void setImage(int index, byte[] data) {
        switch (index) {
            case 1 -> image1 = data;
            case 2 -> image2 = data;
            case 3 -> image3 = data;
            case 4 -> image4 = data;
            case 5 -> image5 = data;
            case 6 -> image6 = data;
            case 7 -> image7 = data;
            case 8 -> image8 = data;
            case 9 -> image9 = data;
            case 10 -> image10 = data;
            default -> throw new IllegalArgumentException("Image index must be 1–10");
        }
    }


}
