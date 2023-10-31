package com.grievanceredressalsystem.analyticsservice.mock.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Department {
    private UUID department_id;
    private String name;
}
