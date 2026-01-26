package com.kdu.smarthome.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeviceDto {
    
    private UUID roomId;
    private UUID deviceId;
}
