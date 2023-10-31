package com.grievanceredressalsystem.analyticsservice.services;

import com.grievanceredressalsystem.analyticsservice.mock.models.Ticket;

import java.util.List;

public interface ExternalTicketService {
    public List<Ticket> getTickets();

    // Todo : Get Tickets based on date range
    // public List<Ticket> getTicketsBetween(Date start , Date end);
}
