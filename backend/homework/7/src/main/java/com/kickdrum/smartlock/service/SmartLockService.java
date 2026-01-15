package com.kickdrum.smartlock.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kickdrum.smartlock.annotations.Audited;
import com.kickdrum.smartlock.annotations.SecureAction;
import com.kickdrum.smartlock.exception.HardwareFailureException;
import com.kickdrum.smartlock.exception.SmartLockAccessException;


@Service
public class SmartLockService {
    private static final Logger log =
            LoggerFactory.getLogger(SmartLockService.class);
            
    @Audited
    @SecureAction
    public void unlock(String user){
        if(user == null || user.isBlank()){
            throw new HardwareFailureException("User cannot be null");
        }
        if(user.equalsIgnoreCase("Unknown")){
           throw new SmartLockAccessException("SECURITY ALERT: Unauthorized access blocked!" );
        }

        log.info("The door is now open for {}", user);
    }

    @SecureAction
    public void checkBattery(){

        log.info("Battery status is OK.");
    }
}
