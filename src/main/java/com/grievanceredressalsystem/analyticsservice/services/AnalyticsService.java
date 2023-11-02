package com.grievanceredressalsystem.analyticsservice.services;

import com.grievanceredressalsystem.analyticsservice.mock.models.Department;
import com.grievanceredressalsystem.analyticsservice.mock.models.Region;
import com.grievanceredressalsystem.analyticsservice.mock.models.Ticket;
import com.grievanceredressalsystem.analyticsservice.mock.models.TicketStatus;
import com.grievanceredressalsystem.analyticsservice.models.RangeFrequency;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AnalyticsService {
    public Map<String, Long> getTicketsBasedOnStatus(Date startDate, Date endDate , RangeFrequency frequency, UUID department_id , UUID region_id, TicketStatus status);
}
