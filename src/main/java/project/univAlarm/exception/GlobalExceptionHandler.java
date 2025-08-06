package project.univAlarm.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import project.univAlarm.controller.ApiResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handle404(NoHandlerFoundException e) {
        return ApiResponse.notFound("resource not found");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handle403(AccessDeniedException e) {
        return ApiResponse.unauthorized("access denied.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handle500(Exception e) {
        e.printStackTrace();
        return ApiResponse.internalServerError(e.getMessage());
    }

}

