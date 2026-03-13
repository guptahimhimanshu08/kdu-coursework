package com.kickdrum.jpaproject.dto;

import lombok.Data;

@Data
public class CreateShiftUserRequest {
    private String shiftId;
    private String userId;

    public String getShiftId() { return shiftId; }
    public String getUserId() { return userId; }
}
