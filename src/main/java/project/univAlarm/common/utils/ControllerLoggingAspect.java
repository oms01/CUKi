package project.univAlarm.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ControllerLoggingAspect {

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object logControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("API 실행 시작: {}", methodName);

        Object result;
        try {
            result = joinPoint.proceed();
            log.info("API 실행 성공: {}", methodName);
            return result;
        } catch (Throwable e) {
            log.error("API 실행 중 오류 발생: {} - {}", methodName, e.getMessage());
            throw e;
        }
    }
}