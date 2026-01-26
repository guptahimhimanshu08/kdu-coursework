package com.kdu.smarthome.exceptions;

public class RegisteredDeviceNotFoundException extends RuntimeException {
    public RegisteredDeviceNotFoundException(String message) {
        super(message);
    }
}
