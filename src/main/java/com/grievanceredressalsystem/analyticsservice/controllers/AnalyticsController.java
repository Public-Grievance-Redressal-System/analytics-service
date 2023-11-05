package com.grievanceredressalsystem.analyticsservice.controllers;

import com.grievanceredressalsystem.analyticsservice.dtos.GenerateReportDTO;
import com.grievanceredressalsystem.analyticsservice.mock.models.Department;
import com.grievanceredressalsystem.analyticsservice.mock.models.Region;
import com.grievanceredressalsystem.analyticsservice.mock.models.Ticket;
import com.grievanceredressalsystem.analyticsservice.mock.models.TicketStatus;
import com.grievanceredressalsystem.analyticsservice.models.RangeFrequency;
import com.grievanceredressalsystem.analyticsservice.models.Report;
import com.grievanceredressalsystem.analyticsservice.services.AnalyticsService;
import com.grievanceredressalsystem.analyticsservice.services.AuthenticationService;
import com.grievanceredressalsystem.analyticsservice.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/analytics")
@CrossOrigin
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private ReportService reportService;
    @GetMapping
    public ResponseEntity<String> hello(){
        ResponseEntity<String> responseEntity = new ResponseEntity("API works", HttpStatus.OK);
        return responseEntity;
    }
    @GetMapping("/metrics/tickets")
    public Map<String, Long> fetchTickets(@RequestParam @DateTimeFormat(pattern="ddMMyyyy") Date from, @RequestParam @DateTimeFormat(pattern="ddMMyyyy") Date to, @RequestParam RangeFrequency rangeFrequency, @RequestParam(required = false) UUID department_id, @RequestParam(required = false) UUID region_id, @RequestParam TicketStatus status,@RequestHeader String Authorization) throws Exception {
        authenticationService.authenticate(Authorization,department_id,region_id);
        return analyticsService.getTicketsBasedOnStatus(from,to,rangeFrequency,department_id,region_id,status);
    }

    @GetMapping("/metrics/average-resolution-time")
    public Map<String, Double> fetchAverageResolutionTime(@RequestParam @DateTimeFormat(pattern="ddMMyyyy") Date from, @RequestParam @DateTimeFormat(pattern="ddMMyyyy") Date to, @RequestParam RangeFrequency rangeFrequency, @RequestParam(required = false) UUID department_id, @RequestParam(required = false) UUID region_id,@RequestHeader String Authorization) throws Exception {
        authenticationService.authenticate(Authorization,department_id,region_id);
        return analyticsService.getAverageResolutionTime(from,to,rangeFrequency,department_id,region_id);
    }

    @PostMapping("/generate-report")
    public ResponseEntity<String> generateReport(@RequestBody GenerateReportDTO generateReportDTO,@RequestHeader String Authorization) throws Exception {
        authenticationService.authenticate(Authorization,generateReportDTO.getDepartment_id(),generateReportDTO.getRegion_id());
        return analyticsService.generateReport(generateReportDTO.getMetricType(),generateReportDTO.getFrom(),generateReportDTO.getTo(),generateReportDTO.getRangeFrequency(),generateReportDTO.getDepartment_id(),generateReportDTO.getRegion_id(),generateReportDTO.getStatus());
    }
    @GetMapping("/reports")
    public List<Report> getAllReports(){
        return reportService.getAllReports();
    }
}
