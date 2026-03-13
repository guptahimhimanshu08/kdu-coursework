package com.kickdrum.jpaproject.dto;

import java.util.List;

import lombok.Data;

@Data
public class TenantOnboardingRequest {

    private List<CreateShiftTypeRequest> shiftTypes;
    private List<CreateShiftRequest> shifts;
    private List<CreateUserRequest> users;

    public List<CreateShiftTypeRequest> getShiftTypes() { return shiftTypes; }
    public List<CreateShiftRequest> getShifts() { return shifts; }
    public List<CreateUserRequest> getUsers() { return users; }
}