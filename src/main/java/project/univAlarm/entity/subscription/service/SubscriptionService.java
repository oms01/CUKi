package project.univAlarm.entity.subscription.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.entity.notificationType.domain.NotificationType;
import project.univAlarm.entity.user.domain.User;
import project.univAlarm.entity.subscription.domain.Subscription;
import project.univAlarm.entity.notificationType.repository.NotificationTypeRepository;
import project.univAlarm.entity.user.repository.UserRepository;
import project.univAlarm.entity.subscription.repository.SubscriptionRepository;
import project.univAlarm.entity.user.dto.UserSubscriptionResponseDto;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final NotificationTypeRepository notificationTypeRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserSubscriptionResponseDto> findByUser(Long userId){
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);
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
        Subscription subscription = subscriptionRepository.save(new Subscription(user.get(), notificationType));
        return new UserSubscriptionResponseDto(subscription);
    }

    @Transactional
    public void delete(Long userId, Long userSubscriptionId){
        subscriptionRepository.findById(userSubscriptionId).ifPresent(userSubscription -> {
            if(!userId.equals(userSubscription.getUser().getId())) {
                throw new AccessDeniedException("Access denied");
            }
            subscriptionRepository.delete(userSubscription);
        });
    }
}
