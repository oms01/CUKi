package project.univAlarm.domain.subscription.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.univAlarm.common.ApiResponse;
import project.univAlarm.domain.subscription.dto.CreateSubscriptionRequestDto;
import project.univAlarm.domain.subscription.dto.DeleteSubscriptionRequestDto;
import project.univAlarm.common.docs.SubscriptionControllerDocs;
import project.univAlarm.common.security.jwt.dto.CustomUserDetails;
import project.univAlarm.domain.subscription.service.SubscriptionService;
import project.univAlarm.domain.user.dto.UserSubscriptionResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController implements SubscriptionControllerDocs {
    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserSubscriptionResponseDto>>> getSubscriptions(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        Long userId = Long.valueOf(userDetails.getUsername());
        List<UserSubscriptionResponseDto> subscriptions = subscriptionService.findByUser(userId);
        return ApiResponse.ok(subscriptions);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createSubscription(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CreateSubscriptionRequestDto requestDto
    ){
        Long userId = Long.valueOf(userDetails.getUsername());
        List<Long> notificationTypeIds = requestDto.getNotificationTypeIds();
        for (Long notificationTypeId : notificationTypeIds) {
            subscriptionService.save(userId, notificationTypeId);
        }
        return ApiResponse.noContent();
    }


    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteSubscription(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody DeleteSubscriptionRequestDto requestDto
    ){
        Long userId = Long.valueOf(userDetails.getUsername());
        Long notificationTypeId = requestDto.getUserSubscriptionId();

        subscriptionService.delete(userId, notificationTypeId);
        return ApiResponse.noContent();
    }
}
