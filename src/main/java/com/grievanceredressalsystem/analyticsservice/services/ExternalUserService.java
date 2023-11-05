package com.grievanceredressalsystem.analyticsservice.services;

import com.grievanceredressalsystem.analyticsservice.mock.models.User;

import java.util.List;
import java.util.UUID;

public interface ExternalUserService {
    public List<User> getAllUsers();
    public boolean validateDepartment(UUID user_id, UUID department_id);
    public boolean validateRegion(UUID user_id,UUID region_id);
}
