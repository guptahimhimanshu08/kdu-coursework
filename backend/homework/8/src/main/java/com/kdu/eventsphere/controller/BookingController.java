package com.kdu.eventsphere.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdu.eventsphere.domain.event.TicketBookedEvent;
import com.kdu.eventsphere.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final ApplicationEventPublisher publisher;

    public BookingController(
            BookingService bookingService,
            ApplicationEventPublisher publisher
    ) {
        this.bookingService = bookingService;
        this.publisher = publisher;
    }

    @PostMapping
    public String book() {
        return bookingService.bookTicket("user-101", "A1");
    }

    // Test idempotency
    @PostMapping("/duplicate-payment")
    public void duplicatePaymentTest() {

        TicketBookedEvent event =
                new TicketBookedEvent("BOOK-123", "user-101", "A1", "500");

        publisher.publishEvent(event);
        publisher.publishEvent(event);
        publisher.publishEvent(event);
    }
}
