package com.kickdrum.jpaproject.dto;

import java.util.UUID;

import lombok.Data;

@Data
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
