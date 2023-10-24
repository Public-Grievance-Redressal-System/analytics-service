package com.grievanceredressalsystem.analyticsservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {
    @GetMapping
    public ResponseEntity<String> hello(){
        ResponseEntity<String> responseEntity = new ResponseEntity("API works", HttpStatus.OK);
        return responseEntity;
    }
}
