package com.kickdrum.hospitalStaffingApp.model;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String username;
    private boolean loggedIn;
    private String timezone;
    private UUID tenantId;

}
