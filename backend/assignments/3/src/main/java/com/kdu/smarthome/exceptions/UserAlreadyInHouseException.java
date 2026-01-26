package com.kdu.smarthome.exceptions;

public class UserAlreadyInHouseException extends RuntimeException {
    public UserAlreadyInHouseException(String message) {
        super(message);
    }
}
