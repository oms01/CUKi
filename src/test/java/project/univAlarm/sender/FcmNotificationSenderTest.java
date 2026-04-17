package project.univAlarm.sender;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.univAlarm.batch.sender.dto.PushNotificationDto;
import project.univAlarm.batch.sender.sender.FcmNotificationSender;


//@SpringBootTest
class FcmNotificationSenderTest {

//    @Autowired
    private FcmNotificationSender sender;

//    @Test
    public void FcmSenderTest() {
        String token = "TEMP_TOKEN";
        PushNotificationDto dummyPushNotificationDto = createDummyPushNotificationDto();
        sender.send(token,dummyPushNotificationDto);
    }
    private PushNotificationDto createDummyPushNotificationDto() {
        PushNotificationDto pushNotificationDto = new PushNotificationDto();
        pushNotificationDto.setSchoolName("가톨릭대학교");
        pushNotificationDto.setKind("장학");
        pushNotificationDto.setTitle("[학생지원팀](한국장학재단)2025-2 국가근로장학생 선발 관련 재단/트리니티 희망근로지 신청 안내(8/1~8/11)\n");
        pushNotificationDto.setDate("2025.07.28");
        pushNotificationDto.setWriter("학생지원팀");
        pushNotificationDto.setLink("https://www.catholic.ac.kr/ko/campuslife/notice.do?mode=view&articleNo=262710&article.offset=0&articleLimit=10");
        return pushNotificationDto;
    }
}