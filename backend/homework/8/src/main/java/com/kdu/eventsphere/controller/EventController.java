package com.kdu.eventsphere.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kdu.eventsphere.dto.request.CreateEventRequest;
import com.kdu.eventsphere.dto.response.EventResponse;
import com.kdu.eventsphere.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    // create event request takes event name, ticket count, event date
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody CreateEventRequest request) {

        EventResponse eventResponse = eventService.addEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventResponse);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse> updateEvent(@Valid @RequestBody CreateEventRequest request,
        @RequestParam UUID id
    ) {

        EventResponse eventResponse = eventService.updateEvent(request, id);
        return ResponseEntity.status(HttpStatus.OK).body(eventResponse);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<EventResponse>> getAllEvents(Pageable pageable){
        
        Page<EventResponse> events = eventService.getAll(pageable);

        return ResponseEntity.ok(events);
    }
}
