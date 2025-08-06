package project.univAlarm.sender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PushNotificationReport {
    private int totalCount;
    private int successCount;
    private int failureCount;
    private PushNotificationDto notification;

    @Override
    public String toString() {
        String title = notification.getTitle();
        String shortenTitle = title.length() > 35 ? title.substring(0, 32)+"..." : title;
        return String.format(
                "## :rotating_light: Push Notification Report\n" +
                        "\uD83D\uDD38 %s: [%s](<%s>)\n" +
                        "``` 전체 전송 시도: %d\n" +
                        " 성공 전송: %d\n" +
                        " 실패 전송: %d```\n",
                notification.getSchoolName(), shortenTitle, notification.getLink(), totalCount, successCount, failureCount
        );
    }

    public void addCount(int totalCount,  int successCount, int failureCount) {
        this.totalCount += totalCount;
        this.successCount += successCount;
        this.failureCount += failureCount;
    }

    public void addSuccess() {
        successCount++;
    }

    public void addFailure() {
        failureCount++;
    }

}
