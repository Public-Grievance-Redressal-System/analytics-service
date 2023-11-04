package com.grievanceredressalsystem.analyticsservice.mock.services;

import com.grievanceredressalsystem.analyticsservice.mock.models.Department;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class MockDepartmentService {
    private static List<Department> departments;

    public static void createRandomDepartments(int n){
        departments = new ArrayList<>();
//        for(int i=0;i<n;i++)
//            departments.add(new Department(UUID.randomUUID(),Faker.instance().commerce().department()));
        departments.add(new Department(UUID.fromString("75677a61-2420-41af-be68-d673175a5b9f"),"Shoes"));
        departments.add(new Department(UUID.fromString("5a185a54-32b6-438f-a8a5-3f815bcd88a7"),"Beauty"));
        departments.add(new Department(UUID.fromString("4b2f45f8-2673-4193-af70-102e9865a7b7"),"Outdoors"));
        departments.add(new Department(UUID.fromString("e2b0ac51-50ab-4794-bd1d-35d5b4d15c41"),"Industrial"));
        departments.add(new Department(UUID.fromString("606967f2-26c5-42d1-a043-d5bef5ecda8d"),"Automotive"));
    }
    public Department getRandomDepartment(){
        Random random = new Random();
        return departments.get(random.nextInt(departments.size()));
    }
}
