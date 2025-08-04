package project.univAlarm.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.domain.NotificationType;
import project.univAlarm.domain.User;
import project.univAlarm.domain.UserSubscription;
import project.univAlarm.repository.NotificationTypeRepository;
import project.univAlarm.repository.UserRepository;
import project.univAlarm.repository.UserSubscriptionRepository;
import project.univAlarm.service.dto.UserSubscriptionResponseDto;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final NotificationTypeRepository notificationTypeRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserSubscriptionResponseDto> findByUser(Long userId){
        List<UserSubscription> subscriptions = userSubscriptionRepository.findByUserId(userId);
        return subscriptions.stream()
                .map(UserSubscriptionResponseDto::new)
                .toList();
    }

    @Transactional
    public UserSubscriptionResponseDto save(Long userId, Long notificationTypeId){
        Optional<NotificationType> notificationTypeOptional = notificationTypeRepository.findById(notificationTypeId);
        Optional<User> userOptional = userRepository.findById(userId);
        if(notificationTypeOptional.isEmpty() || userOptional.isEmpty()){
            return null;
        }
        NotificationType notificationType = notificationTypeOptional.get();
        User user = userOptional.get();
        UserSubscription subscription = userSubscriptionRepository.save(new UserSubscription(user, notificationType));
        return new UserSubscriptionResponseDto(subscription);
    }

    @Transactional
    public void delete(Long userSubscriptionId){
        userSubscriptionRepository.deleteById(userSubscriptionId);
    }
}
