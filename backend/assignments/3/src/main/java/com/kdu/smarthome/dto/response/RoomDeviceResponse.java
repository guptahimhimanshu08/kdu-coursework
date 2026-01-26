package com.kdu.smarthome.dto.response;

import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoomDeviceResponse {

        private UUID roomId;
        private Set<UUID> deviceIds;    

}
