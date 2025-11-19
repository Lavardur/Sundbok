package is.hi.hbv501g.sundbok.repository;

import is.hi.hbv501g.sundbok.model.Notification;
import is.hi.hbv501g.sundbok.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {

    // Unread first, newest first
    List<Notification> findByUserOrderByReadAscCreatedAtDesc(User user);
}
