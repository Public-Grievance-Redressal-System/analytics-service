package com.grievanceredressalsystem.analyticsservice.services;

import com.grievanceredressalsystem.analyticsservice.mock.services.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class AuthenticationService {

    @Autowired
    private ExternalUserService externalUserService;

    @Autowired
    private MockService mockService;
    public boolean authenticate(String authString,UUID department_id,UUID region_id) throws Exception {
        mockService.createMockData();
        String[] tokens = authString.split(" ");
        System.out.println(Arrays.toString(tokens));
        if(tokens[1].equals("SUPER_ADMIN")){
            return true;
        }
        if(!tokens[1].equals("ADMIN")){
            throw new Exception("You are not Authorized");
        }
        UUID user_id = UUID.fromString(tokens[2]); // For mocking purpose passing user_id in authentication in real screnario will get this from user service and jwt token
        if(department_id!=null && !externalUserService.validateDepartment(user_id,department_id)){
            throw new Exception("You are not Authorized to access data for this Department");
        }
        if(region_id!=null && !externalUserService.validateRegion(user_id,region_id)){
            throw new Exception("You are not Authorized to access data for this Region");
        }
        return true;
    }
}
