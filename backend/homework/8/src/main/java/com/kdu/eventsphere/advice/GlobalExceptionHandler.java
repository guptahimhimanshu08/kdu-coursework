package com.kdu.eventsphere.advice;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kdu.eventsphere.dto.error.ApiErrorResponse;

import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
     private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(OptimisticLockException.class)
        public ResponseEntity<ApiErrorResponse> handleOptimisticLock(
                OptimisticLockException ex,
                HttpServletRequest request
        ) {
            logger.warn("Optimistic lock exception on {}: {}", request.getRequestURI(), ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(error(
                            request,
                            "CONCURRENT_MODIFICATION",
                            "Book was modified concurrently",
                            null
                    ));
        } 
        
    private ApiErrorResponse error(
                HttpServletRequest request,
                String code,
                String message,
                List<ApiErrorResponse.FieldError> details
        ) {
                return new ApiErrorResponse(
                        Instant.now(),
                        request.getRequestURI(),
                        code,
                        message,
                        details
                );
        }
}
