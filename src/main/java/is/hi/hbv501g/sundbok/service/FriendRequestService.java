package is.hi.hbv501g.sundbok.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import is.hi.hbv501g.sundbok.model.FriendRequest;
import is.hi.hbv501g.sundbok.model.FriendRequestDto;
import is.hi.hbv501g.sundbok.model.User;
import is.hi.hbv501g.sundbok.repository.FriendRequestRepository;
import is.hi.hbv501g.sundbok.repository.UserRepository;

@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public FriendRequestService(
            FriendRequestRepository friendRequestRepository,
            UserRepository userRepository,
            UserService userService
    ) {
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public void sendFriendRequest(Long senderId, Long receiverId) {
        if (senderId.equals(receiverId)) {
            throw new RuntimeException("Cannot send friend request to yourself");
        }

        User sender = userRepository.findById(senderId).orElseThrow();
        User receiver = userRepository.findById(receiverId).orElseThrow();

        if (sender.getFriends().contains(receiver)) {
            throw new RuntimeException("Users are already friends");
        }

        boolean alreadyPending =
                friendRequestRepository.existsBySenderAndReceiverAndStatus(sender, receiver, "PENDING") ||
                friendRequestRepository.existsBySenderAndReceiverAndStatus(receiver, sender, "PENDING");

        if (alreadyPending) {
            throw new RuntimeException("Friend request already exists");
        }

        FriendRequest request = new FriendRequest(sender, receiver, "PENDING");
        friendRequestRepository.save(request);
    }

    @Transactional(readOnly = true)
    public List<FriendRequestDto> getPendingFriendRequests(Long userId) {
        return friendRequestRepository.findByReceiverIdAndStatus(userId, "PENDING")
                .stream()
                .map(FriendRequestDto::new)
                .toList();
    }

    @Transactional
    public void acceptFriendRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findByIdAndStatus(requestId, "PENDING")
                .orElseThrow(() -> new RuntimeException("Pending friend request not found"));

        userService.addFriendship(
                request.getReceiver().getId(),
                request.getSender().getId()
        );

        request.setStatus("ACCEPTED");
        friendRequestRepository.save(request);
    }

    @Transactional
    public void rejectFriendRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findByIdAndStatus(requestId, "PENDING")
                .orElseThrow(() -> new RuntimeException("Pending friend request not found"));

        request.setStatus("REJECTED");
        friendRequestRepository.save(request);
    }

    @Transactional(readOnly = true)
    public FriendRequest getRequestById(Long requestId) {
        return friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));
    }
}