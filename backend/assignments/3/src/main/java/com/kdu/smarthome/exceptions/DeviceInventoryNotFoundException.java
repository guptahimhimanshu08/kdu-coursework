package com.kdu.smarthome.exceptions;

public class DeviceInventoryNotFoundException extends RuntimeException {
    public DeviceInventoryNotFoundException(String message) {
        super(message);
    }
}
