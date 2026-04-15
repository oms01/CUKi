package project.univAlarm.common.docs;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import project.univAlarm.common.ApiResponse;
import project.univAlarm.domain.subscription.dto.CreateSubscriptionRequestDto;
import project.univAlarm.domain.subscription.dto.DeleteSubscriptionRequestDto;
import project.univAlarm.common.security.jwt.dto.CustomUserDetails;
import project.univAlarm.domain.user.dto.UserSubscriptionResponseDto;

@Tag(name = "Subscription Controller", description = "사용자 구독 관련 API 입니다.")
public interface SubscriptionControllerDocs {
    
    @CustomOperation(summary = "사용자가 구독한 공지 타입 목록 가져오기")
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserSubscriptionResponseDto>>> getSubscriptions(
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @CustomOperation(summary = "구독 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createSubscription(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CreateSubscriptionRequestDto requestDto
    );


    @CustomOperation(summary = "구독 해제")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteSubscription(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody DeleteSubscriptionRequestDto requestDto
    );

}
