package com.grievanceredressalsystem.analyticsservice.mock.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Region {
    private UUID region_id;
    private String name;
}
