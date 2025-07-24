package project.univAlarm.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_subscriptions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"device_id", "notification_type_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private UserDevice device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_type_id", nullable = false)
    private NotificationType notificationType;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public UserSubscription(UserDevice device, NotificationType notificationType) {
        this.device = device;
        this.notificationType = notificationType;
    }
}