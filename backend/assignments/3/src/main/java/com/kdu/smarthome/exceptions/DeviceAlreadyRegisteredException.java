package com.kdu.smarthome.exceptions;

public class DeviceAlreadyRegisteredException extends RuntimeException {
    public DeviceAlreadyRegisteredException(String message) {
        super(message);
    }
}
