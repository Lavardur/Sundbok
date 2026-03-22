package is.hi.hbv501g.sundbok.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import is.hi.hbv501g.sundbok.auth.UserPrincipal;
import is.hi.hbv501g.sundbok.model.FriendRequest;
import is.hi.hbv501g.sundbok.model.FriendRequestDto;
import is.hi.hbv501g.sundbok.service.FriendRequestService;

@RestController
@RequestMapping("/api")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    private boolean isSelf(Long userId, org.springframework.security.core.Authentication auth) {
        if (auth == null || auth.getPrincipal() == null) return false;
        var principal = (UserPrincipal) auth.getPrincipal();
        return principal.id().equals(userId);
    }

    @PostMapping("/friend-requests")
    public ResponseEntity<Void> sendFriendRequest(
            @RequestParam Long senderId,
            @RequestParam Long receiverId,
            org.springframework.security.core.Authentication auth
    ) {
        if (!isSelf(senderId, auth)) {
            return ResponseEntity.status(403).build();
        }

        try {
            friendRequestService.sendFriendRequest(senderId, receiverId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/users/{userId}/friend-requests")
    public ResponseEntity<List<FriendRequestDto>> getPendingFriendRequests(
            @PathVariable Long userId,
            org.springframework.security.core.Authentication auth
    ) {
        if (!isSelf(userId, auth)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(friendRequestService.getPendingFriendRequests(userId));
    }

    @PostMapping("/friend-requests/{requestId}/accept")
    public ResponseEntity<Void> acceptFriendRequest(
            @PathVariable Long requestId,
            org.springframework.security.core.Authentication auth
    ) {
        FriendRequest request = friendRequestService.getRequestById(requestId);

        if (!isSelf(request.getReceiver().getId(), auth)) {
            return ResponseEntity.status(403).build();
        }

        try {
            friendRequestService.acceptFriendRequest(requestId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/friend-requests/{requestId}/reject")
    public ResponseEntity<Void> rejectFriendRequest(
            @PathVariable Long requestId,
            org.springframework.security.core.Authentication auth
    ) {
        FriendRequest request = friendRequestService.getRequestById(requestId);

        if (!isSelf(request.getReceiver().getId(), auth)) {
            return ResponseEntity.status(403).build();
        }

        try {
            friendRequestService.rejectFriendRequest(requestId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}