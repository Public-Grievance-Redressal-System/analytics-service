package com.grievanceredressalsystem.analyticsservice.mock.services;

import com.github.javafaker.Faker;
import com.grievanceredressalsystem.analyticsservice.mock.models.*;
import com.grievanceredressalsystem.analyticsservice.services.ExternalTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MockTicketService implements ExternalTicketService {

    @Autowired
    private MockUserService mockUserService;

    @Autowired
    private MockDepartmentService mockDepartmentService;

    @Autowired
    private MockRegionService mockRegionService;

    @Autowired
    private MockRoleService mockRoleService;

    private static List<Ticket> tickets;

    @Override
    public List<Ticket> getTickets(){
        return tickets;
    }
    public void createTickets() {
        tickets = new ArrayList<>();

        for(int i=0;i<100;i++){
            tickets.add(createRandomOpenTicket());
        }
        for(int i=0;i<100;i++){
            tickets.add(createRandomResolvedTicket());
        }
    }
    Ticket createRandomResolvedTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicket_id(UUID.randomUUID());
        ticket.setOpenedBy(mockUserService.createRandomUser());
        Random random = new Random();
        ticket.setAssignedTo(mockUserService.getRandomRedressor());
        Date now = new Date();
        long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        long oneDayInMillis = 24 * 60 * 60 * 1000; // 1 Day in milliseconds
        long oneMonthInMillis = 28L * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        ticket.setOpened_date_time(Faker.instance().date().between(new Date(now.getTime()-oneMonthInMillis),now));
        ticket.setClosing_date_time(Faker.instance().date().between(ticket.getOpened_date_time(),new Date(ticket.getOpened_date_time().getTime()+oneDayInMillis/4)));
        ticket.setDepartment(mockDepartmentService.getRandomDepartment());
        ticket.setRegion(mockRegionService.getRandomRegion());
        ticket.setStatus(TicketStatus.RESOLVED);
        // Set other attributes
        return ticket;
    }
    public Ticket createRandomOpenTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicket_id(UUID.randomUUID());
        ticket.setOpenedBy(mockUserService.createRandomUser());
        Random random = new Random();
        ticket.setAssignedTo(mockUserService.getRandomRedressor());
        Date now = new Date();
        long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        long oneDayInMillis = 24 * 60 * 60 * 1000; // 1 Day in milliseconds
        long oneMonthInMillis = 28L * 24 * 60 * 60 * 1000; // 1 month in milliseconds
        ticket.setOpened_date_time(Faker.instance().date().between(new Date(now.getTime()-12*oneMonthInMillis),now));
        ticket.setDepartment(mockDepartmentService.getRandomDepartment());
        ticket.setRegion(mockRegionService.getRandomRegion());
        ticket.setStatus(TicketStatus.OPEN);
        // Set other attributes
        return ticket;
    }
    public void randomlyResolveTickets(List<Ticket> tickets){
        Random random = new Random();
        // Resolve half tickets randomly
        Date now = new Date();
        long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        long oneDayInMillis = 24 * 60 * 60 * 1000; // 1 Day in milliseconds
        long oneMonthInMillis = 28L * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        for(int i=0;i<tickets.size()/2;i++){
            Ticket ticket = tickets.get(random.nextInt(tickets.size()));
            ticket.setClosing_date_time(Faker.instance().date().between(ticket.getOpened_date_time(),new Date(ticket.getOpened_date_time().getTime()+oneDayInMillis/4)));
            ticket.setStatus(TicketStatus.RESOLVED);
        }
    }
}
