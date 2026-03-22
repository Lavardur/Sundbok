package is.hi.hbv501g.sundbok.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "friend_requests")
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(nullable = false)
    private String status; // PENDING, ACCEPTED, REJECTED

    public FriendRequest() {}

    public FriendRequest(User sender, User receiver, String status) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}