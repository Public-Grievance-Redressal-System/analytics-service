package com.grievanceredressalsystem.analyticsservice;

import com.grievanceredressalsystem.analyticsservice.mock.services.MockService;
import com.grievanceredressalsystem.analyticsservice.mock.services.MockTicketService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnalyticsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyticsServiceApplication.class, args);
    }

}
