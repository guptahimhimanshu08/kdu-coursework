package com.kdu.smarthome.dto.request;

import lombok.Data;

@Data
public class RegisterDeviceRequest {
    private String kickstonId;
    private String deviceUsername;
    private String devicePassword;
}
