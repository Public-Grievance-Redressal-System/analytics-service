package com.grievanceredressalsystem.analyticsservice.mock.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private UUID role_id;
    private String role;
}
