package com.kdu.eventsphere.dto.request;
import jakarta.validation.constraints.NotBlank;


public class CreateTicketRequest {
    
    @NotBlank
    private String eventName;

    public String getEventName() {
        return eventName;
    }
}
