package com.grievanceredressalsystem.analyticsservice.mock.services;

import com.github.javafaker.Faker;
import com.grievanceredressalsystem.analyticsservice.mock.models.Role;
import com.grievanceredressalsystem.analyticsservice.mock.models.User;
import com.grievanceredressalsystem.analyticsservice.services.ExternalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("mockUserService")
public class MockUserService implements ExternalUserService {

    @Autowired
    private static MockDepartmentService mockDepartmentService;

    @Autowired
    private static MockRegionService mockRegionService;

    @Autowired
    private static MockRoleService mockRoleService;
    @Override
    public List<User> getAllUsers() {
        return null;
    }
    public static User createRandomUser() {
        User user = new User();
        user.setUser_id(UUID.randomUUID());
        user.setName(Faker.instance().name().firstName());
        user.setDepartment(mockDepartmentService.getRandomDepartment());
        user.setRegion(mockRegionService.getRandomRegion());
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(mockRoleService.getUser());
        user.setRoles(roleSet);
        Date now = new Date();
        long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        long oneDayInMillis = 24 * 60 * 60 * 1000; // 1 Day in milliseconds
        long oneMonthInMillis = 28L * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        user.setLastLoginTime(Faker.instance().date().between(new Date(now.getTime() - oneMonthInMillis),now));
        return user;
    }
    public static User createRandomAdmin(){
        User user = new User();
        user.setUser_id(UUID.randomUUID());
        user.setName(Faker.instance().name().firstName());
        user.setDepartment(mockDepartmentService.getRandomDepartment());
        user.setRegion(mockRegionService.getRandomRegion());
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(mockRoleService.getUser());
        roleSet.add(mockRoleService.getAdmin());
        user.setRoles(roleSet);
        Date now = new Date();
        long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        long oneDayInMillis = 24 * 60 * 60 * 1000; // 1 Day in milliseconds
        long oneMonthInMillis = 28L * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        user.setLastLoginTime(Faker.instance().date().between(new Date(now.getTime() - oneMonthInMillis),now));
        return user;
    }
    public static User createRandomRedressor(){
        User user = new User();
        user.setUser_id(UUID.randomUUID());
        user.setName(Faker.instance().name().firstName());
        user.setDepartment(mockDepartmentService.getRandomDepartment());
        user.setRegion(mockRegionService.getRandomRegion());
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(mockRoleService.getUser());
        roleSet.add(mockRoleService.getRedressor());
        user.setRoles(roleSet);
        Date now = new Date();
        long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        long oneDayInMillis = 24 * 60 * 60 * 1000; // 1 Day in milliseconds
        long oneMonthInMillis = 28L * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        user.setLastLoginTime(Faker.instance().date().between(new Date(now.getTime() - oneMonthInMillis),now));
        return user;
    }
}
