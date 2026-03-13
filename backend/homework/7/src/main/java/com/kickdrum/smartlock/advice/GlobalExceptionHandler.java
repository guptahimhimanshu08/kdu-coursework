package com.kickdrum.smartlock.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kickdrum.smartlock.exception.HardwareFailureException;
import com.kickdrum.smartlock.exception.SmartLockAccessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HardwareFailureException.class)
    public ResponseEntity<String> hardwareException(HardwareFailureException ex) {
        return ResponseEntity.status(500).body("Hardware Failure: " + ex.getMessage());
    }

    @ExceptionHandler(SmartLockAccessException.class)
    public ResponseEntity<String> accessException(SmartLockAccessException ex) {
        return ResponseEntity.status(403).body("Access Denied: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> generalException(Exception ex) {
        return ResponseEntity.status(500).body("An error occurred: " + ex.getMessage());
    }

}
