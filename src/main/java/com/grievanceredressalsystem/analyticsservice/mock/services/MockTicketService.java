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
    private static List<User> admins;
    private static List<User> users;
    @Override
    public List<Ticket> getTickets() {
        List<Ticket> tickets = new ArrayList<>();
        createRandomDepartments();
        createRandomRegions();
        createRandomRedressors();
        createRandomAdmins();
        createRandomUsers();
        for(int i=0;i<100;i++){
            tickets.add(createRandomOpenTicket());
        }
        for(int i=0;i<100;i++){
            tickets.add(createRandomResolvedTicket());
        }
        return tickets;
    }

    public static void createRandomDepartments(){
        departments = new ArrayList<>();
//        for(int i=0;i<n;i++)
//            departments.add(new Department(UUID.randomUUID(),Faker.instance().commerce().department()));
        departments.add(new Department(UUID.fromString("75677a61-2420-41af-be68-d673175a5b9f"),"Shoes"));
        departments.add(new Department(UUID.fromString("5a185a54-32b6-438f-a8a5-3f815bcd88a7"),"Beauty"));
        departments.add(new Department(UUID.fromString("4b2f45f8-2673-4193-af70-102e9865a7b7"),"Outdoors"));
        departments.add(new Department(UUID.fromString("e2b0ac51-50ab-4794-bd1d-35d5b4d15c41"),"Industrial"));
        departments.add(new Department(UUID.fromString("606967f2-26c5-42d1-a043-d5bef5ecda8d"),"Automotive"));
    }
    public static void createRandomRegions(){
        regions = new ArrayList<>();
//        for(int i=0;i<n;i++)
//            regions.add(new Region(UUID.randomUUID(),Faker.instance().address().cityName()));
        regions.add(new Region(UUID.fromString("c8bdbb5f-689d-42de-996b-fbd31990fbf9"),"North Merrilltown"));
        regions.add(new Region(UUID.fromString("33d2e874-5e08-41e2-b535-cf75748a2da7"),"Port Julian"));
        regions.add(new Region(UUID.fromString("602df2f8-6450-4f1a-aa70-61283a8792de"),"Wilbershire"));
        regions.add(new Region(UUID.fromString("382e4cdf-7e39-4635-960c-cdb03408ea63"),"North Octaviotown"));
        regions.add(new Region(UUID.fromString("f633bfea-bb6b-4a0e-b3f6-938681bc3d79"),"Koelpinbury"));
    }
    public static void createRandomRedressors(){
        redressors = new ArrayList<>();
        for(int i=0;i<3;i++)
            redressors.add(mockUserService.createRandomRedressor());

        redressors.get(0).setUser_id(UUID.fromString("e538ebdd-37d9-4f66-a21b-f54fd7026ea8"));
        redressors.get(1).setUser_id(UUID.fromString("617ac0ad-99e6-4807-9a43-c4c1aab5590d"));
        redressors.get(2).setUser_id(UUID.fromString("8715a2e3-aaad-4a87-a006-370271813954"));
    }
    public static void createRandomAdmins(){
        admins = new ArrayList<>();
        for(int i=0;i<2;i++)
            admins.add(mockUserService.createRandomAdmin());

        admins.get(0).setUser_id(UUID.fromString("5ed94f67-097b-49ca-b579-b5c8afeaee7a"));
        admins.get(1).setUser_id(UUID.fromString("38443a5a-00c8-4c8e-a2f9-48d3d125a9f1"));
    }
    public static void createRandomUsers(){
        users = new ArrayList<>();
        for(int i=0;i<5;i++){
            users.add(mockUserService.createRandomUser());
        }
        users.get(0).setUser_id(UUID.fromString("a90597af-6867-457e-b7c5-a2e72b0c9dd6"));
        users.get(1).setUser_id(UUID.fromString("ddcf2b69-e14e-4185-b00f-2d8ac465d4bd"));
        users.get(2).setUser_id(UUID.fromString("be6318d5-4035-4a2f-97ea-231cf1db71eb"));
        users.get(3).setUser_id(UUID.fromString("54117b13-c044-4ffc-a97d-26b7a9431383"));
        users.get(4).setUser_id(UUID.fromString("dcf2e361-2ab0-4aa7-b743-f8db62a2100b"));
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
