package com.kdu.eventsphere.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kdu.eventsphere.domain.event.Event;
import com.kdu.eventsphere.domain.ticket.Ticket;
import com.kdu.eventsphere.domain.ticket.TicketRepository;
import com.kdu.eventsphere.dto.request.BuyTicketRequest;
import com.kdu.eventsphere.dto.response.BookedTicketResponse;
import com.kdu.eventsphere.dto.response.ReservedTicketResponse;
import com.kdu.eventsphere.mapper.EventMapper;
import com.kdu.eventsphere.mapper.TicketMapper;

import jakarta.transaction.Transactional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final EventService eventService;
    private final EventMapper eventMapper;
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper,EventMapper eventMapper, EventService eventService) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.eventMapper = eventMapper;
        this.eventService = eventService;
    }

    @Transactional
    public BookedTicketResponse bookTickets(UUID reservedTicketId) {
       
        logger.info("Booking tickets for reservation ID: {}", reservedTicketId);

        Ticket ticket = ticketRepository.findById(reservedTicketId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation ID: " + reservedTicketId));
        
        /** 
         * mock transaction
        */ 
        UUID transactionId = transaction();

        ticket.markBooked();

        ticket.setTransactionId(transactionId);

        ticket = ticketRepository.save(ticket);

        logger.info("Ticket booked successfully with ID: {}", ticket.getId());

        return ticketMapper.toBookedResponse(ticket);
    }

    public ReservedTicketResponse reserveTickets(BuyTicketRequest request) {
        
        if(request.getEventName() == null || request.getEventName().trim().isEmpty()){
            logger.error("Invalid event name: {}", request.getEventName());
            throw new IllegalArgumentException("Event name is required");
        }

        logger.info("Reserving tickets for event: {}", request.getEventName());

      
        Event event = eventService.getEventByName(request.getEventName());
        
        if(event.getTicketCount() == null){
            logger.error("No tickets available for event: {}", request.getEventName());
            throw new IllegalArgumentException("No tickets available for event: " + request.getEventName());
        }
        
        Ticket ticket = new Ticket();

        ticket.setEventName(event.getEventName());
        
                
        ticket.setEventDate(event.getEventDate());
        ticket.setPrice(100.0);
        ticket.setStatus(com.kdu.eventsphere.domain.enums.Status.AVAILABLE);

        ticket.markReserved();

        ticket = ticketRepository.save(ticket);
        
        event.setTicket(ticket);
        event.setTicketCount(event.getTicketCount() - 1);
        eventService.updateEvent(eventMapper.toRequest(event), event.getId());

        logger.info("Ticket reserved successfully with ID: {}", ticket.getId());

        return ticketMapper.toResponse(ticket);
    }

    public UUID transaction(){
        logger.info("Transaction performed");

        return UUID.randomUUID();
    }
}
