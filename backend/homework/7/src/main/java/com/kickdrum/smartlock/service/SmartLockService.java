package com.kickdrum.smartlock.service;

import org.springframework.stereotype.Service;

import com.kickdrum.smartlock.annotations.Audited;
import com.kickdrum.smartlock.annotations.SecureAction;
import com.kickdrum.smartlock.exception.HardwareFailureException;
import com.kickdrum.smartlock.exception.SmartLockAccessException;


@Service
public class SmartLockService {
    
    @Audited
    @SecureAction
    public void unlock(String user){
        if(user == null || user.isBlank()){
            throw new HardwareFailureException("User cannot be null");
        }
        if(user.equalsIgnoreCase("Unknown")){
           throw new SmartLockAccessException("SECURITY ALERT: Unauthorized access blocked!" );
        }

        System.out.println("The door is now open for " + user);
    }

    @SecureAction
    public void checkBattery(){

        System.out.println("Battery status is OK.");
    }
}
