package com.kickdrum.smartlock.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class SmartLockAspect {

    private static final Logger log =
            LoggerFactory.getLogger(SmartLockAspect.class);


    @Before("@annotation(com.kickdrum.smartlock.annotations.Audited) && args(user)")
    public void logBeforeUnlock(String user){
        log.info("ACCESS ATTEMPT: User is approaching the door");
        
    }

    @AfterReturning("@annotation(com.kickdrum.smartlock.annotations.Audited) && args(user)")
    public void logAfterUnlock(String user){
        log.info("SUCCESS: User has entered the building");
    }

    @Around("execution(* com.kickdrum.smartlock.service.SmartLockService.checkBattery(..))")
    public void aroundCheckBattery(ProceedingJoinPoint proceedJoinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        try {
            proceedJoinPoint.proceed();
        } catch (Throwable e) {
            log.error("ERROR during battery check: {}", e.getMessage());
        }
        long end = System.currentTimeMillis();

        log.info("Battery check took {} ms", (end - start));
    }

    @Around("@annotation(com.kickdrum.smartlock.annotations.SecureAction) && args(user)")
    public Object passcodeCheck(ProceedingJoinPoint proceedJoinPoint, String user) {

        try {
            return proceedJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @AfterThrowing(
        pointcut = "@annotation(com.kickdrum.smartlock.annotations.SecureAction)",
        throwing = "exception"
    )
    public void triggerAlarm(Exception exception) {
        log.error("ALARM TRIGGERED: System error detected : {}", exception.getMessage());
    }

}
