package com.grievanceredressalsystem.analyticsservice.config;

import com.grievanceredressalsystem.analyticsservice.mock.services.MockTicketService;
import com.grievanceredressalsystem.analyticsservice.mock.services.MockUserService;
import com.grievanceredressalsystem.analyticsservice.services.ExternalTicketService;
import com.grievanceredressalsystem.analyticsservice.services.ExternalUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ServiceConfig {
    @Profile("development")
    @Bean("externalTicketService")
    public ExternalTicketService externalTicketService() {
        return new MockTicketService();
    }
    @Bean
    public ExternalUserService externalUserService() {
        return new MockUserService();
    }

//    @Profile("production")
//    @Bean
//    public ExternalService externalService() {
//        return new ActualExternalService();
//    }
}
