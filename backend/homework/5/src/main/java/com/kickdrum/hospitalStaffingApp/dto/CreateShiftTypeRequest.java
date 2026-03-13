package com.kickdrum.hospitalStaffingApp.dto;

import java.util.UUID;

public class CreateShiftTypeRequest {

    private String name;
    private String description;
    private boolean activeStatus;
    private UUID tenantId;

    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isActiveStatus() { return activeStatus; }
    public UUID getTenantId() { return tenantId; }
}
