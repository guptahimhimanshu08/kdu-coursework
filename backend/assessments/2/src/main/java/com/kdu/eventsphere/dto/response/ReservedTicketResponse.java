package com.kdu.eventsphere.dto.response;

import java.time.Instant;
import java.util.UUID;


public class ReservedTicketResponse {

    private UUID id;
    private String title;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
    private String eventDate;
    public ReservedTicketResponse(
            UUID id,
            String title,
            String status,
            Instant createdAt,
            Instant updatedAt,
            String eventDate

    ) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.eventDate = eventDate;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public String getEventDate() { return eventDate; }
    
}
