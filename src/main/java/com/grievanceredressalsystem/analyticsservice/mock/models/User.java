package com.grievanceredressalsystem.analyticsservice.mock.models;

import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private String name;
    private UUID user_id;
    private Date lastLoginTime;
    private Set<Role> roles;
    private Department department;
    private Region region;
}
