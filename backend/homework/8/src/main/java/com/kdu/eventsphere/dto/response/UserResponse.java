package com.kdu.eventsphere.dto.response;

import java.time.Instant;
import java.util.UUID;

public class UserResponse {

    private UUID id;
    private String username;
    private String role;
    private Instant createdAt;

    public UserResponse(
            UUID id,
            String username,
            String role,
            Instant createdAt
    ) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }


    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
