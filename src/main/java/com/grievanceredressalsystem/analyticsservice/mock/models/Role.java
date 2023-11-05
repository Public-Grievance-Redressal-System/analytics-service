package com.grievanceredressalsystem.analyticsservice.mock.models;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {
    private UUID role_id;
    private String role;
}
