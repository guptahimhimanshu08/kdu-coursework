package com.kdu.smarthome.advice;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kdu.smarthome.dto.error.ApiErrorResponse;
import com.kdu.smarthome.exceptions.DeviceAlreadyAssignedToRoomException;
import com.kdu.smarthome.exceptions.DeviceAlreadyRegisteredException;
import com.kdu.smarthome.exceptions.DeviceCredentialMismatchException;
import com.kdu.smarthome.exceptions.DeviceHouseMismatchException;
import com.kdu.smarthome.exceptions.DeviceInventoryNotFoundException;
import com.kdu.smarthome.exceptions.HouseNotFoundException;
import com.kdu.smarthome.exceptions.RegisteredDeviceNotFoundException;
import com.kdu.smarthome.exceptions.RoomNotFoundException;
import com.kdu.smarthome.exceptions.UnauthorizedAccessException;
import com.kdu.smarthome.exceptions.UserAlreadyInHouseException;
import com.kdu.smarthome.exceptions.UserNotFoundException;

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
                                "Resource was modified by another request. Please retry.",
                                null
                        ));
        }
        
        /** Resource Not Found Exceptions */
        @ExceptionHandler({
                UserNotFoundException.class,
                HouseNotFoundException.class,
                RoomNotFoundException.class,
                RegisteredDeviceNotFoundException.class,
                DeviceInventoryNotFoundException.class
        })
        public ResponseEntity<ApiErrorResponse> handleNotFound(
                RuntimeException ex,
                HttpServletRequest request
        ) {
                logger.warn("Resource not found on {}: {}", request.getRequestURI(), ex.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(error(
                                request,
                                "RESOURCE_NOT_FOUND",
                                ex.getMessage(),
                                null
                        ));
        }
        
        /** Business Logic / Conflict Exceptions */
        @ExceptionHandler({
                DeviceAlreadyRegisteredException.class,
                DeviceAlreadyAssignedToRoomException.class,
                UserAlreadyInHouseException.class
        })
        public ResponseEntity<ApiErrorResponse> handleConflict(
                RuntimeException ex,
                HttpServletRequest request
        ) {
                logger.warn("Conflict exception on {}: {}", request.getRequestURI(), ex.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(error(
                                request,
                                "RESOURCE_CONFLICT",
                                ex.getMessage(),
                                null
                        ));
        }
        
        @ExceptionHandler(UnauthorizedAccessException.class)
        public ResponseEntity<ApiErrorResponse> handleUnauthorizedAccess(
                UnauthorizedAccessException ex,
                HttpServletRequest request
        ) {
                logger.warn("Unauthorized access on {}: {}", request.getRequestURI(), ex.getMessage());
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(error(
                                request,
                                "UNAUTHORIZED_ACCESS",
                                ex.getMessage(),
                                null
                        ));
        }
        
        @ExceptionHandler({
                DeviceCredentialMismatchException.class,
                DeviceHouseMismatchException.class
        })
        public ResponseEntity<ApiErrorResponse> handleBadRequest(
                RuntimeException ex,
                HttpServletRequest request
        ) {
                logger.warn("Bad request on {}: {}", request.getRequestURI(), ex.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(error(
                                request,
                                "BAD_REQUEST",
                                ex.getMessage(),
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
