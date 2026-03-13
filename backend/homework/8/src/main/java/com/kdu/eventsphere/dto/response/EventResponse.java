package com.kdu.eventsphere.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.kdu.eventsphere.domain.ticket.Ticket;

public class EventResponse {

    private UUID id;
    private String eventName;
    private Integer ticketCount;
    private Instant createdAt;
    private String eventDate;
    private Ticket ticket;

    public EventResponse(UUID id, String eventName, Integer ticketCount, Ticket ticket, Instant createdAt, String eventDate) {
        this.id = id;
        this.eventName = eventName;
        this.ticketCount = ticketCount;
        this.ticket = ticket;
        this.createdAt = createdAt;
        this.eventDate = eventDate;
    }

    public UUID getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }
    
    public Ticket getTicket() {
        return ticket;
    }
    public Integer getTicketCount() {
        return ticketCount;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    
    public String getEventDate() {
        return eventDate;
    }
}
