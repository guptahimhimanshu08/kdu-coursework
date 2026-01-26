package com.kdu.smarthome.exceptions;

public class DeviceAlreadyAssignedToRoomException extends RuntimeException {
    public DeviceAlreadyAssignedToRoomException(String message) {
        super(message);
    }
}
