package com.kdu.eventsphere.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.kdu.eventsphere.domain.event.Event;
import com.kdu.eventsphere.domain.event.EventRepository;
import com.kdu.eventsphere.dto.request.CreateEventRequest;
import com.kdu.eventsphere.dto.response.EventResponse;
import com.kdu.eventsphere.mapper.EventMapper;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EventService {
    
    private final EventRepository  eventRepository;
    private final EventMapper eventMapper;  
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    public EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    public EventResponse addEvent(CreateEventRequest request) {
        
  
        if(request.getTicketCount() == null || request.getTicketCount() <= 0){
            logger.error("Invalid ticket count: {}", request.getTicketCount());
            throw new IllegalArgumentException("Ticket count must be a positive integer");
        }

        logger.info("Creating event with name: {}", request.getEventName());
        
        Event event = eventMapper.toEntity(request);

        if(event == null){
            logger.error("Failed to create event entity from request: {}", request);
            throw new IllegalArgumentException("Invalid event data");
        }
        event.setTicketCount(request.getTicketCount());
        Event savedEvent = eventRepository.save(event);
        
        logger.info("Event created successfully with ID: {}", savedEvent.getId());

        return eventMapper.toResponse(savedEvent);

    }

    
    public EventResponse updateEvent(CreateEventRequest request, UUID eventId) {
        
        logger.info("Updating event with ID: {}", eventId);
        
        Event existingEvent = eventRepository.findById(eventId)
            .orElseThrow(() -> new IllegalArgumentException("Event not found with ID: " + eventId));

        if (request.getEventName() != null) {
            existingEvent.setEventName(request.getEventName());
        }
        if (request.getEventDate() != null) {
            existingEvent.setEventDate(request.getEventDate());
        }
        if (request.getTicketCount() != null) {
            if(request.getTicketCount() <= 0){
                logger.error("Invalid ticket count: {}", request.getTicketCount());
                throw new IllegalArgumentException("Ticket count must be a positive integer");
            }
            existingEvent.setTicketCount(request.getTicketCount());
        }

        Event updatedEvent = eventRepository.save(existingEvent);

        logger.info("Event updated successfully with ID: {}", updatedEvent.getId());

        return eventMapper.toResponse(updatedEvent);

    }

    public Page<EventResponse> getAll(Pageable pageable) {
        
        logger.debug("Fetching events with pageable: {}", pageable);
        
         
        if(pageable == null){
            pageable = Pageable.unpaged();
            logger.debug("No pagination provided, defaulting to unpaged");
        }

        Page<Event> events = eventRepository.findAll(pageable);
        
        logger.debug("Found {} events matching criteria", events.getTotalElements());

        return events.map(eventMapper::toResponse);
        
    }

    public Event getEventByName(String eventName) {
        logger.debug("Fetching event with name: {}", eventName);
        
        return eventRepository.findByEventName(eventName)
            .orElseThrow(() -> new IllegalArgumentException("Event not found with name: " + eventName));
        
    }

    /** Get deleted events as iterables */

    // public Iterable<Event> getEvents(boolean isDeleted) {
    //     Session session = entityManager.unwrap(Session.class);
    //     Filter filter = session.enableFilter("deletedFilter");
    //     filter.setParameter("isDeleted", isDeleted);
    //     Iterable<Event> events = eventRepository.findAll();
    //     session.disableFilter("deletedFilter");
    //     return events;
    // }
}


