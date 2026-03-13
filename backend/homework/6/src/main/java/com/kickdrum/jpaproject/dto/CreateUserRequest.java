package com.kickdrum.jpaproject.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    private String username;
    private String timezone;
    private UUID tenantId;

    public String getUsername() { return username; }
    public String getTimezone() { return timezone; }
    public UUID getTenantId() { return tenantId; }
}
 