package project.univAlarm.sender;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.univAlarm.batch.sender.PushNotificationReport;
import project.univAlarm.batch.sender.dto.PushNotificationDto;
import project.univAlarm.batch.sender.sender.DiscordReportSender;
import reactor.core.publisher.Mono;

@SpringBootTest
class DiscordReportSenderTest {

    @Autowired
    private DiscordReportSender discordReportSender;

    @Test
    public void discordReportSenderTest() {
        PushNotificationReport dummyPushNotificationReport = createDummyPushNotificationReport();
        Mono<Boolean> result = discordReportSender.send(dummyPushNotificationReport);
        System.out.println(result.block());
    }

    private PushNotificationReport createDummyPushNotificationReport() {
        PushNotificationReport dummyPushNotificationReport = new PushNotificationReport();
        dummyPushNotificationReport.setNotification(createDummyPushNotificationDto());
        dummyPushNotificationReport.setSuccessCount(8);
        dummyPushNotificationReport.setFailureCount(2);
        dummyPushNotificationReport.setTotalCount(10);
        return dummyPushNotificationReport;
    }

    private PushNotificationDto createDummyPushNotificationDto() {
        PushNotificationDto pushNotificationDto = new PushNotificationDto();
        pushNotificationDto.setSchoolName("가톨릭대학교/성심교정");
        pushNotificationDto.setKind("장학");
        pushNotificationDto.setTitle("[학생지원팀](한국장학재단)2025-2 국가근로장학생 선발 관련 재단/트리니티 희망근로지 신청 안내(8/1~8/11)\n");
        pushNotificationDto.setDate("2025.07.28");
        pushNotificationDto.setWriter("학생지원팀");
        pushNotificationDto.setLink("https://www.catholic.ac.kr/ko/campuslife/notice.do?mode=view&articleNo=262710&article.offset=0&articleLimit=10");
        return pushNotificationDto;
    }
}