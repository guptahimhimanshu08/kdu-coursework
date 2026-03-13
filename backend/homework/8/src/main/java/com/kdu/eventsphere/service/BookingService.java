package com.kdu.eventsphere.service;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final ApplicationEventPublisher eventPublisher;

    public BookingService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public String bookTicket(String userId, String seatNumber) {

        String bookingId = UUID.randomUUID().toString();

        System.out.println("Booking started for bookingId=" + bookingId);

        // Publish event (NON-BLOCKING)
        eventPublisher.publishEvent(
                new TicketBookedEvent(bookingId, userId, seatNumber, 500.0)
        );

        // Immediate response
        return "Booking in progress for bookingId=" + bookingId;
    }
}
