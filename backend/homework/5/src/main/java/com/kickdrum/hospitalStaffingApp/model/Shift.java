package com.kickdrum.hospitalStaffingApp.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shift {

    private UUID id;
    private UUID shiftTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private UUID tenantId;

    public UUID getId() { return id; }
    public UUID getShiftTypeId() { return shiftTypeId; }
    public UUID getTenantId() { return tenantId; }
}

