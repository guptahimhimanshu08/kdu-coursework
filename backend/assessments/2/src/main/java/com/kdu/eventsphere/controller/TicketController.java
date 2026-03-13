package com.kdu.eventsphere.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kdu.eventsphere.dto.request.BuyTicketRequest;
import com.kdu.eventsphere.dto.response.BookedTicketResponse;
import com.kdu.eventsphere.dto.response.ReservedTicketResponse;
import com.kdu.eventsphere.service.TicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    
    private final TicketService ticketService;
   
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // book tickets, count must be positive integer
    @PostMapping("/book/{id}")
    public ResponseEntity<BookedTicketResponse> bookTickets(@Valid @PathVariable UUID id) {
        
        BookedTicketResponse response = ticketService.bookTickets(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reserve")
    public ResponseEntity<ReservedTicketResponse> reserveTickets(@Valid @RequestBody BuyTicketRequest request) {
     
        ReservedTicketResponse response = ticketService.reserveTickets(request);
        return ResponseEntity.ok(response);
    }
   
}
