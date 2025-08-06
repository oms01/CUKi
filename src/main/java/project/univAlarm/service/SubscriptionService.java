package project.univAlarm.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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
        Optional<User> user = userRepository.findById(userId);
        if(notificationTypeOptional.isEmpty() || user.isEmpty()){
            return null;
        }
        NotificationType notificationType = notificationTypeOptional.get();
        UserSubscription subscription = userSubscriptionRepository.save(new UserSubscription(user.get(), notificationType));
        return new UserSubscriptionResponseDto(subscription);
    }

    @Transactional
    public void delete(Long userId, Long userSubscriptionId){
        userSubscriptionRepository.findById(userSubscriptionId).ifPresent(userSubscription -> {
            if(!userId.equals(userSubscription.getUser().getId())) {
                throw new AccessDeniedException("Access denied");
            }
            userSubscriptionRepository.delete(userSubscription);
        });
    }
}
