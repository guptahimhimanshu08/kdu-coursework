package com.kickdrum.smartlock.controller;

import org.springframework.web.bind.annotation.*;

import com.kickdrum.smartlock.service.SmartLockService;

@RestController
@RequestMapping("/smartlock")
public class SmartLockController {

    private final SmartLockService smartLockService;

    public SmartLockController(SmartLockService smartLockService) {
        this.smartLockService = smartLockService;
    }
    
    @PostMapping("/unlock")
    public void unlockDoor(@RequestParam(required = false) String user){
        smartLockService.unlock(user);
    }

    @GetMapping("/battery")
    public void checkBattery(){
        smartLockService.checkBattery();
    }
}
