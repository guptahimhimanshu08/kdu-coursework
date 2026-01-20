package com.kdu.eventsphere.dto.error;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorResponse {

    private Instant timestamp;
    private String path;
    private String errorCode;
    private String message;
    private List<FieldError> details;

    public record FieldError(String field, String issue) {}
}
