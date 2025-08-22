package project.univAlarm.subscription.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import project.univAlarm.notificationType.domain.NotificationType;
import project.univAlarm.user.domain.User;

@Entity
@Table(name = "user_subscriptions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "notification_type_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_type_id", nullable = false)
    private NotificationType notificationType;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public UserSubscription(User user, NotificationType notificationType) {
        this.user = user;
        this.notificationType = notificationType;
    }
}