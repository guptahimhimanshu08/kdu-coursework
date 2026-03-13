package com.kickdrum.hospitalStaffingApp.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftType {

    private UUID id;
    private String name;
    private String description;
    private boolean activeStatus;
    private UUID tenantId;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isActiveStatus() { return activeStatus; }
    public UUID getTenantId() { return tenantId; }
}
