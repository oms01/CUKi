package project.univAlarm.notification.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import project.univAlarm.external.crawler.CrawledNotificationDto;
import project.univAlarm.notificationType.domain.NotificationType;

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

    @Column(name = "origin_id", nullable = false)
    private Long originId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String date;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Notification(NotificationType notificationType, CrawledNotificationDto crawledNotificationDto) {
        this.notificationType = notificationType;
        this.originId = crawledNotificationDto.getId();
        this.title = crawledNotificationDto.getTitle();
        this.writer = crawledNotificationDto.getWriter();
        this.link = crawledNotificationDto.getLink();
        this.date = crawledNotificationDto.getDate();
    }

}
