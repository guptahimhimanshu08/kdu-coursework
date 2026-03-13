package com.kickdrum.hospitalStaffingApp.dto;

import java.util.List;

import com.kickdrum.hospitalStaffingApp.model.Shift;
import com.kickdrum.hospitalStaffingApp.model.ShiftType;
import com.kickdrum.hospitalStaffingApp.model.User;

public class TenantOnboardingRequest {

    private List<ShiftType> shiftTypes;
    private List<Shift> shifts;
    private List<User> users;

    public List<ShiftType> getShiftTypes() { return shiftTypes; }
    public List<Shift> getShifts() { return shifts; }
    public List<User> getUsers() { return users; }
}