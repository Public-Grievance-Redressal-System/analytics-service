package com.grievanceredressalsystem.analyticsservice.config;

import com.grievanceredressalsystem.analyticsservice.dtos.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(Exception ex){
        ResponseEntity<ErrorResponseDTO> responseEntity = new ResponseEntity<>(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }
}
