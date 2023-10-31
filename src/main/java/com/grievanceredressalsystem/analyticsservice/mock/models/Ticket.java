package com.grievanceredressalsystem.analyticsservice.mock.models;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ticket {
    private UUID ticket_id;
    Date opened_date_time;
    Date closing_date_time;
    User openedBy;
    User assignedTo;
    Department department;
    Region region;
    TicketStatus status;

}
