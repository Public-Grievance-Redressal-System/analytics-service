package com.grievanceredressalsystem.analyticsservice.services;

import com.grievanceredressalsystem.analyticsservice.mock.models.Department;
import com.grievanceredressalsystem.analyticsservice.mock.models.Region;
import com.grievanceredressalsystem.analyticsservice.mock.models.Ticket;
import com.grievanceredressalsystem.analyticsservice.mock.models.TicketStatus;
import com.grievanceredressalsystem.analyticsservice.mock.services.MockTicketService;
import com.grievanceredressalsystem.analyticsservice.models.RangeFrequency;
import com.grievanceredressalsystem.analyticsservice.utils.StringDateComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsServiceImpl implements AnalyticsService{
    public AnalyticsServiceImpl(@Qualifier("externalTicketService") ExternalTicketService ticketService) {
        this.ticketService = ticketService;
    }

    private ExternalTicketService ticketService;

    private static boolean isDateInRange(Date date , Date startDate , Date endDate){
        return date.after(startDate) && date.before(endDate);
    }
    // Truncate the time part from a Date
    private static Date truncateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    @Override
    public Map<String, Long> getTicketsBasedOnStatus(Date startDate, Date endDate, RangeFrequency frequency, UUID department_id, UUID region_id, TicketStatus status) {
        List<Ticket> tickets = ticketService.getTickets();
        List<Ticket> filteredTickets = tickets.stream()
                .filter(ticket -> status.equals(ticket.getStatus())
                        && isDateInRange(ticket.getOpened_date_time(), startDate, endDate))
                .collect(Collectors.toList());

        if(department_id!=null) {
            filteredTickets = filteredTickets.stream()
                    .filter(ticket -> department_id.equals(ticket.getDepartment().getDepartment_id()))
                    .collect(Collectors.toList());
        }
        if(region_id!=null){
            filteredTickets = filteredTickets.stream()
                    .filter(ticket -> region_id.equals(ticket.getRegion().getRegion_id()))
                    .collect(Collectors.toList());
        }

        List<Integer> ticketResponse = new ArrayList<>();
        Map<String, Long> counts = new TreeMap<>(new StringDateComparator());

        if(frequency == RangeFrequency.DAILY){
            // Group and count tickets by day (ignoring time)
            Map<String, Long> dailyCounts = filteredTickets.stream()
                    .collect(Collectors.groupingBy(
                            ticket -> new SimpleDateFormat("dd/MM/yyyy").format(truncateTime(status==TicketStatus.OPEN ? ticket.getOpened_date_time():ticket.getClosing_date_time())),
                            Collectors.counting()
                    ));
            counts.putAll(dailyCounts);
        }
        if(frequency==RangeFrequency.MONTHLY){
            // Group and count tickets by month (MM/yyyy, ignoring time)
            Map<String, Long> monthlyCounts = filteredTickets.stream()
                    .collect(Collectors.groupingBy(
                            ticket -> new SimpleDateFormat("MM/yyyy").format(truncateTime(status==TicketStatus.OPEN ? ticket.getOpened_date_time():ticket.getClosing_date_time())),
                            Collectors.counting()
                    ));
            counts.putAll(monthlyCounts);
        }
        if(frequency == RangeFrequency.YEARLY){
            Map<String, Long> yearlyCounts = filteredTickets.stream()
                    .collect(Collectors.groupingBy(
                            ticket -> {
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(truncateTime(status==TicketStatus.OPEN ? ticket.getOpened_date_time():ticket.getClosing_date_time()));
                                return Integer.toString(cal.get(Calendar.YEAR));
                            },
                            Collectors.counting()
                    ));
            counts.putAll(yearlyCounts);
        }
        System.out.println(counts);
//        System.out.println(tickets);
        return counts;
    }
}
