package is.hi.hbv501g.sundbok.service;

import is.hi.hbv501g.sundbok.model.Notification;
import is.hi.hbv501g.sundbok.model.User;
import is.hi.hbv501g.sundbok.repository.NotificationRepository;
import is.hi.hbv501g.sundbok.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class NotificationService {

    private final NotificationRepository notifications;
    private final UserRepository users;

    public NotificationService(NotificationRepository notifications, UserRepository users) {
        this.notifications = notifications;
        this.users = users;
    }

    @Transactional
    public void createForUsers(Set<User> recipients, Notification.Type type, String message, Long facilityId, Long actorUserId) {
        for (User u : recipients) {
            Notification n = new Notification(u, type, message, facilityId, actorUserId);
            notifications.save(n);
        }
    }

    @Transactional(readOnly = true)
    public List<Notification> getForUser(Long userId) {
        User u = users.findById(userId).orElseThrow();
        return notifications.findByUserOrderByReadAscCreatedAtDesc(u);
    }

    @Transactional
    public void markRead(Long userId, Long notificationId) {
        Notification n = notifications.findById(notificationId).orElseThrow();
        if (!n.getUser().getId().equals(userId)) {
            throw new RuntimeException("Cannot modify someone else's notification");
        }
        n.setRead(true);
    }
}
