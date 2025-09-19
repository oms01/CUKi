package project.univAlarm.common.exception;

import javax.naming.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import project.univAlarm.common.ApiResponse;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handle401(AuthenticationException e) {
        return ApiResponse.unauthorized("Authentication failed");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handle403(AccessDeniedException e) {
        return ApiResponse.unauthorized("access denied.");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handle404(NoHandlerFoundException e) {
        return ApiResponse.notFound("resource not found");
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handle500(Exception e) {
        log.error(e.getMessage());
        return ApiResponse.internalServerError(null);
    }

}

