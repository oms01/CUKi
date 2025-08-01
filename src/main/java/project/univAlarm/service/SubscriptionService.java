package project.univAlarm.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.domain.User;
import project.univAlarm.domain.UserSubscription;
import project.univAlarm.repository.UserSubscriptionRepository;
import project.univAlarm.service.dto.UserSubscriptionResponseDto;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final UserSubscriptionRepository userSubscriptionRepository;

    @Transactional(readOnly = true)
    public List<UserSubscriptionResponseDto> findByUser(User user){
        List<UserSubscription> subscriptions = userSubscriptionRepository.findByUserId(user.getId());
        return subscriptions.stream()
                .map(UserSubscriptionResponseDto::new)
                .toList();
    }

    @Transactional
    public UserSubscriptionResponseDto save(UserSubscription userSubscription){
        UserSubscription subscription = userSubscriptionRepository.save(userSubscription);
        return new UserSubscriptionResponseDto(subscription);
    }

    @Transactional
    public void delete(UserSubscription userSubscription){
        userSubscriptionRepository.delete(userSubscription);
    }
}
