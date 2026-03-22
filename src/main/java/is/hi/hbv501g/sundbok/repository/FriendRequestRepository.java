package is.hi.hbv501g.sundbok.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import is.hi.hbv501g.sundbok.model.FriendRequest;
import is.hi.hbv501g.sundbok.model.User;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long> {

    List<FriendRequest> findByReceiverIdAndStatus(Long receiverId, String status);

    boolean existsBySenderAndReceiverAndStatus(User sender, User receiver, String status);

    boolean existsBySenderAndReceiver(User sender, User receiver);

    Optional<FriendRequest> findByIdAndStatus(Long id, String status);
}