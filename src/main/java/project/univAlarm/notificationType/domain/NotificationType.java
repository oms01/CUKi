package project.univAlarm.notificationType.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import project.univAlarm.notification.domain.Notification;
import project.univAlarm.school.domain.School;
import project.univAlarm.subscription.domain.UserSubscription;

@Entity
@Table(name = "notification_types")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(nullable = false)
    private String name;

    @Column(name = "is_department", nullable = false)
    private Boolean isDepartment;

    @Column(nullable = false)
    private String url;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "notificationType")
    private List<UserSubscription> userSubscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "notificationType")
    private List<Notification> notifications = new ArrayList<>();

    public NotificationType(School school, String name, Boolean isDepartment, String url) {
        this.school = school;
        this.name = name;
        this.isDepartment = isDepartment;
        this.url = url;
    }

    @Getter @Setter
    public static class NotificationTypeResponseDto {
        private Long id;
        private String schoolName;
        private String name;
        private boolean isDepartment;
        private String url;

        public NotificationTypeResponseDto(NotificationType notificationType) {
            this.id = notificationType.getId();
            this.schoolName = notificationType.getSchool().getName();
            this.name = notificationType.getName();
            this.isDepartment = notificationType.getIsDepartment();
            this.url = notificationType.getUrl();
        }
    }
}