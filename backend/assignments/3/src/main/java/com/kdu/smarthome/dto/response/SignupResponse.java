package com.kdu.smarthome.dto.response;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupResponse {
    private String username;
    private Instant createdAt;
    private UUID id;
}
