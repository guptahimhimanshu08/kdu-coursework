package com.kdu.eventsphere.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record BookTicketRequest(
    @NotBlank(message = "Event ID is required")
    String eventId,
    
    @NotBlank(message = "Seat number is required")
    String seatNo,
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    String phoneNumber
) {
}
