package com.kickdrum.hospitalStaffingApp.dto;

import java.util.UUID;

public class CreateUserRequest {

    private String username;
    private String timezone;
    private UUID tenantId;

    public String getUsername() { return username; }
    public String getTimezone() { return timezone; }
    public UUID getTenantId() { return tenantId; }
}
 