package is.hi.hbv501g.sundbok.model;

public class FriendRequestDto {

    private Long id;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private String status;

    public FriendRequestDto() {}

    public FriendRequestDto(FriendRequest request) {
        this.id = request.getId();
        this.senderId = request.getSender().getId();
        this.senderName = request.getSender().getName();
        this.receiverId = request.getReceiver().getId();
        this.receiverName = request.getReceiver().getName();
        this.status = request.getStatus();
    }

    public Long getId() {
        return id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getStatus() {
        return status;
    }
}