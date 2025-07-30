package project.univAlarm.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.univAlarm.domain.User;
import project.univAlarm.domain.UserSubscription;
import project.univAlarm.repository.UserSubscriptionRepository;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final UserSubscriptionRepository userSubscriptionRepository;

    public List<UserSubscription> findByUser(User user){
        return userSubscriptionRepository.findByUserId(user.getId());
    }

    public UserSubscription save(UserSubscription userSubscription){
        return userSubscriptionRepository.save(userSubscription);
    }

    public void delete(UserSubscription userSubscription){
        userSubscriptionRepository.delete(userSubscription);
    }
}
