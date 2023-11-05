package com.grievanceredressalsystem.analyticsservice.mock.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MockService {
    @Autowired
    private MockTicketService mockTicketService;

    @Autowired
    private MockUserService mockUserService;

    @Autowired
    private MockDepartmentService mockDepartmentService;

    @Autowired
    private MockRegionService mockRegionService;

    @Autowired
    private MockRoleService mockRoleService;

    public void createMockData(){
        mockUserService.createUsers();
        mockDepartmentService.createRandomDepartments();
        mockRegionService.createRandomRegions();
        mockRoleService.createRandomRoles();
        mockTicketService.createTickets();
    }
}
