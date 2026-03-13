package com.kdu.eventsphere.service;

public record TicketBookedEvent(
        String bookingId,
        String userId,
        String seatNumber,
        double amount
) {}
