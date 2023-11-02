package com.grievanceredressalsystem.analyticsservice.mock.services;

import com.github.javafaker.Faker;
import com.grievanceredressalsystem.analyticsservice.mock.models.User;
import com.grievanceredressalsystem.analyticsservice.services.ExternalUserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("mockUserService")
public class MockUserService implements ExternalUserService {

    @Override
    public List<User> getAllUsers() {
        return null;
    }
    public static User createRandomUser() {
        User user = new User();
        user.setUser_id(UUID.randomUUID());
        user.setName(Faker.instance().name().firstName());
        Date now = new Date();
        long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        long oneDayInMillis = 24 * 60 * 60 * 1000; // 1 Day in milliseconds
        long oneMonthInMillis = 28L * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        user.setLastLoginTime(Faker.instance().date().between(new Date(now.getTime() - oneMonthInMillis),now));
        return user;
    }
}
