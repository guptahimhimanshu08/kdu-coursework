package com.kdu.eventsphere.service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    // Acts like a deduplication store
    private final Set<String> processedBookings =
            ConcurrentHashMap.newKeySet();

    @Async
    @EventListener
    public void deductMoney(TicketBookedEvent event) {

        // Idempotency check
        if (!processedBookings.add(event.bookingId())) {
            System.out.println(
                "[Payment] Duplicate payment ignored for bookingId=" +
                event.bookingId()
            );
            return;
        }

        System.out.println(
            "[Payment] Money deducted for bookingId=" +
            event.bookingId() + ", amount=" + event.amount()
        );
    }
}
