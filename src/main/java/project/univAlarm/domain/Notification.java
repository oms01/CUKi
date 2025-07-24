package project.univAlarm.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications",
        indexes = @Index(name = "idx_notification_type", columnList = "notification_type_id"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_type_id", nullable = false)
    private NotificationType notificationType;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 50)
    private String writer;

    @Column(nullable = false, length = 200)
    private String link;

    @Column(nullable = false, length = 50)
    private String date;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Notification(NotificationType notificationType, String title, String writer, String link, String date) {
        this.notificationType = notificationType;
        this.title = title;
        this.writer = writer;
        this.link = link;
        this.date = date;
    }
}
