package com.taskflow.demo.controllers;

import com.taskflow.demo.records.*;
import com.taskflow.demo.services.TicketService;
import com.taskflow.demo.services.UserStoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    @GetMapping("/userstory/{UserStoryId}")
    public List<TicketDto> getTicketByUserStoryId(@PathVariable String UserStoryId) {
        return ticketService.findTicketWithUserByProjectId(UserStoryId);
    }
    @PostMapping("/update")
    public CreateResponse UpdateUserStory(@RequestBody TicketUpdateDto request)
    {

        return ticketService.updateTicket(request);
    }
    @PostMapping("/store")
    public CreateResponse storeUserStory(@RequestBody CreateTicketRequest request)
    {
        return ticketService.createTicket(request);
    }
    @GetMapping("/details/{TicketId}")
    public Object getTicketDetails(@PathVariable String TicketId) {
        return ticketService.getTicketDetails(TicketId);
    }
    @PostMapping("/filter")
    public Object filterTickets(@RequestBody TicketFilter filterRequest) {
        Object tickets = ticketService.filterTickets(filterRequest);
        return tickets;
    }
    @GetMapping("/delete/{ticketId}")
    public CreateResponse deleteUserStory(@PathVariable String ticketId) {
       return ticketService.deleteTicket(ticketId);
    }
}
