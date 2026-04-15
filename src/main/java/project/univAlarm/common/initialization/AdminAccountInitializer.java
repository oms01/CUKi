package project.univAlarm.common.initialization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.domain.subscription.service.SubscriptionService;
import project.univAlarm.domain.user.domain.User;
import project.univAlarm.common.security.enums.Role;
import project.univAlarm.domain.user.service.LoginService;
import project.univAlarm.domain.user.dto.UserJoinDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminAccountInitializer {

    private final LoginService loginService;
    private final SubscriptionService subscriptionService;


    @Transactional
    public void init() {
        UserJoinDto adminJoinDto = createAdminJoinDto();

        User admin = loginService.joinProcess(adminJoinDto, Role.ADMIN.getRoles());
        String token = loginService.createToken(admin.getId(), admin.getRole(), 60L*60*24*365*100);
        subscriptionService.save(admin.getId(), 1L);
        subscriptionService.save(admin.getId(), 2L);
        subscriptionService.save(admin.getId(), 3L);
        subscriptionService.save(admin.getId(), 4L);
        log.info("Admin account token : [{}]", token);
    }

    private UserJoinDto createAdminJoinDto() {
        UserJoinDto admin = new UserJoinDto();
        admin.setRole(Role.ADMIN.getRoles());
        admin.setUsername("admin");
        admin.setEmail("admin@admin.com");
        admin.setKakaoId(1L);
        return admin;
    }
}
