package com.kickdrum.hospitalStaffingApp.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftUser {

    private UUID id;
    private UUID shiftId;
    private UUID userId;
    private UUID tenantId;

    public UUID getId() { return id; }
    public UUID getShiftId() { return shiftId; }
    public UUID getUserId() { return userId; }
    public UUID getTenantId() { return tenantId; }
}

