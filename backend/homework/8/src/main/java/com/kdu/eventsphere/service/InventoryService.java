package com.kdu.eventsphere.service;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Async
    @EventListener
    public void handleTicketBooked(TicketBookedEvent event) {
        System.out.println(
            "[Inventory] Seat " + event.seatNumber() +
            " marked occupied for bookingId=" + event.bookingId()
        );
    }
}

