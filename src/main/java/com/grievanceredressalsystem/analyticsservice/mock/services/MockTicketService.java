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

    private static MockUserService mockUserService;

    public MockTicketService(@Qualifier("mockUserService") MockUserService mockUserService) {
        mockUserService = mockUserService;
    }

    private static List<Department> departments;
    private static List<Region> regions;
    private static List<User> redressors;

    @Override
    public List<Ticket> getTickets() {
        List<Ticket> tickets = new ArrayList<>();
        createRandomDepartments(5);
        createRandomRegions(5);
        createRandomRedressors(5);
        for(int i=0;i<100;i++){
            tickets.add(createRandomOpenTicket());
        }
        for(int i=0;i<100;i++){
            tickets.add(createRandomResolvedTicket());
        }
        return tickets;
    }

    public static void createRandomDepartments(int n){
        departments = new ArrayList<>();
//        for(int i=0;i<n;i++)
//            departments.add(new Department(UUID.randomUUID(),Faker.instance().commerce().department()));
        departments.add(new Department(UUID.fromString("75677a61-2420-41af-be68-d673175a5b9f"),"Shoes"));
        departments.add(new Department(UUID.fromString("5a185a54-32b6-438f-a8a5-3f815bcd88a7"),"Beauty"));
        departments.add(new Department(UUID.fromString("4b2f45f8-2673-4193-af70-102e9865a7b7"),"Outdoors"));
        departments.add(new Department(UUID.fromString("e2b0ac51-50ab-4794-bd1d-35d5b4d15c41"),"Industrial"));
        departments.add(new Department(UUID.fromString("606967f2-26c5-42d1-a043-d5bef5ecda8d"),"Automotive"));
    }
    public static void createRandomRegions(int n){
        regions = new ArrayList<>();
//        for(int i=0;i<n;i++)
//            regions.add(new Region(UUID.randomUUID(),Faker.instance().address().cityName()));
        regions.add(new Region(UUID.fromString("c8bdbb5f-689d-42de-996b-fbd31990fbf9"),"North Merrilltown"));
        regions.add(new Region(UUID.fromString("33d2e874-5e08-41e2-b535-cf75748a2da7"),"Port Julian"));
        regions.add(new Region(UUID.fromString("602df2f8-6450-4f1a-aa70-61283a8792de"),"Wilbershire"));
        regions.add(new Region(UUID.fromString("382e4cdf-7e39-4635-960c-cdb03408ea63"),"North Octaviotown"));
        regions.add(new Region(UUID.fromString("f633bfea-bb6b-4a0e-b3f6-938681bc3d79"),"Koelpinbury"));
    }
    public static void createRandomRedressors(int n){
        redressors = new ArrayList<>();
        for(int i=0;i<n;i++)
            redressors.add(mockUserService.createRandomUser());
    }
    public static Ticket createRandomResolvedTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicket_id(UUID.randomUUID());
        ticket.setOpenedBy(MockUserService.createRandomUser());
        Random random = new Random();
        ticket.setAssignedTo(redressors.get(random.nextInt(redressors.size())));
        Date now = new Date();
        long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        long oneDayInMillis = 24 * 60 * 60 * 1000; // 1 Day in milliseconds
        long oneMonthInMillis = 28L * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        ticket.setOpened_date_time(Faker.instance().date().between(new Date(now.getTime()-oneMonthInMillis),now));
        ticket.setClosing_date_time(Faker.instance().date().between(ticket.getOpened_date_time(),new Date(ticket.getOpened_date_time().getTime()+oneDayInMillis/4)));
        ticket.setDepartment(departments.get(random.nextInt(departments.size())));
        ticket.setRegion(regions.get(random.nextInt(regions.size())));
        ticket.setStatus(TicketStatus.RESOLVED);
        // Set other attributes
        return ticket;
    }
    public static Ticket createRandomOpenTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicket_id(UUID.randomUUID());
        ticket.setOpenedBy(MockUserService.createRandomUser());
        Random random = new Random();
        ticket.setAssignedTo(redressors.get(random.nextInt(redressors.size())));
        Date now = new Date();
        long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        long oneDayInMillis = 24 * 60 * 60 * 1000; // 1 Day in milliseconds
        long oneMonthInMillis = 28L * 24 * 60 * 60 * 1000; // 1 month in milliseconds
        ticket.setOpened_date_time(Faker.instance().date().between(new Date(now.getTime()-12*oneMonthInMillis),now));
        ticket.setDepartment(departments.get(random.nextInt(departments.size())));
        ticket.setRegion(regions.get(random.nextInt(regions.size())));
        ticket.setStatus(TicketStatus.OPEN);
        // Set other attributes
        return ticket;
    }
    public static void randomlyResolveTickets(List<Ticket> tickets){
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
