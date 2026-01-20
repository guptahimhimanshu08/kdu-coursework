package com.kdu.eventsphere.service;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Async
    @EventListener
    public void sendSms(TicketBookedEvent event) throws InterruptedException {

        // Simulate slow SMS provider
        Thread.sleep(5000);

        System.out.println(
            "[Notification] SMS sent to user " +
            event.userId() + " for bookingId=" + event.bookingId()
        );
    }
}
