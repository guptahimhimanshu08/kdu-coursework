package com.kickdrum.jpaproject.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private String username;
    private String timezone;

    public String getUsername() { return username; }
    public String getTimezone() { return timezone; }
}
