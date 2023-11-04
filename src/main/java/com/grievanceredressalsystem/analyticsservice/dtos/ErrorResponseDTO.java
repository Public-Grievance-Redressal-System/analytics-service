package com.grievanceredressalsystem.analyticsservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDTO {
    private HttpStatusCode code;
    private String message;
}
