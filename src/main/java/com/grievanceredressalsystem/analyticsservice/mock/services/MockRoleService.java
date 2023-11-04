package com.grievanceredressalsystem.analyticsservice.mock.services;

import com.grievanceredressalsystem.analyticsservice.mock.models.Department;
import com.grievanceredressalsystem.analyticsservice.mock.models.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MockRoleService {
    private static List<Role> roles;

    public static void createRandomRoles(int n){
        roles = new ArrayList<>();
//        for(int i=0;i<n;i++)
//            roles.add(new Role(UUID.randomUUID(),Faker.instance().commerce().department()));
        roles.add(new Role(UUID.fromString("bf8e956e-9150-4288-801b-27db6204e996"),"User"));
        roles.add(new Role(UUID.fromString("c6a700f1-3d04-437b-9a81-9b6f6704ae88"),"Redressor"));
        roles.add(new Role(UUID.fromString("7f071b4d-cfb9-4033-ae52-f04e88695b2f"),"Admin"));
    }
    public Role getRandomRole(){
        Random random = new Random();
        return roles.get(random.nextInt(roles.size()));
    }
    public Role getAdmin(){
        return roles.get(2);
    }
    public Role getRedressor(){
        return roles.get(1);
    }
    public Role getUser(){
        return roles.get(0);
    }
}
