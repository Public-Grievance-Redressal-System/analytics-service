package com.grievanceredressalsystem.analyticsservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.grievanceredressalsystem.analyticsservice.mock.models.TicketStatus;
import com.grievanceredressalsystem.analyticsservice.models.MetricType;
import com.grievanceredressalsystem.analyticsservice.models.RangeFrequency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenerateReportDTO {
    private MetricType metricType;
    @JsonFormat(pattern = "ddMMyyyy")
    private Date from;

    @JsonFormat(pattern = "ddMMyyyy")
    private Date to;
    private RangeFrequency rangeFrequency;
    private UUID department_id;
    private UUID region_id;
    private TicketStatus status;
}
