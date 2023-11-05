package com.grievanceredressalsystem.analyticsservice.mock.services;

import com.github.javafaker.Faker;
import com.grievanceredressalsystem.analyticsservice.mock.models.Role;
import com.grievanceredressalsystem.analyticsservice.mock.models.User;
import com.grievanceredressalsystem.analyticsservice.services.ExternalUserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("mockUserService")
public class MockUserService implements ExternalUserService {
    @Autowired
    private MockDepartmentService mockDepartmentService;
    @Autowired
    private MockRegionService mockRegionService;
    @Autowired
    private MockRoleService mockRoleService;

    private static List<User> users;
    private static int user_cnt;
    private static List<UUID> redressor_ids;
    private static List<UUID> admin_ids;
    private static List<UUID> endUser_ids;

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    public void createUsers(){
        users = new ArrayList<>();
        user_cnt = 0;
        createRandomAdmins();
        createRandomRedressors();
        createRandomEndUsers();
    }
    public void createRandomAdmins(){
        admin_ids = new ArrayList<>();
        for(int i=0;i<2;i++) {
            users.add(createRandomAdmin());
        }
        users.get(user_cnt).setUser_id(UUID.fromString("5ed94f67-097b-49ca-b579-b5c8afeaee7a"));
        users.get(0).getDepartment().setDepartment_id(UUID.fromString("75677a61-2420-41af-be68-d673175a5b9f"));
        users.get(0).getRegion().setRegion_id(UUID.fromString("c8bdbb5f-689d-42de-996b-fbd31990fbf9"));
        admin_ids.add(UUID.fromString("5ed94f67-097b-49ca-b579-b5c8afeaee7a"));
        user_cnt++;
        users.get(user_cnt).setUser_id(UUID.fromString("38443a5a-00c8-4c8e-a2f9-48d3d125a9f1"));
        admin_ids.add(UUID.fromString("38443a5a-00c8-4c8e-a2f9-48d3d125a9f1"));
        user_cnt++;
    }
    public void createRandomRedressors(){
        redressor_ids = new ArrayList<>();
        for(int i=0;i<3;i++)
            users.add(createRandomRedressor());

        users.get(user_cnt).setUser_id(UUID.fromString("e538ebdd-37d9-4f66-a21b-f54fd7026ea8"));
        user_cnt++;
        redressor_ids.add(UUID.fromString("e538ebdd-37d9-4f66-a21b-f54fd7026ea8"));
        users.get(user_cnt).setUser_id(UUID.fromString("617ac0ad-99e6-4807-9a43-c4c1aab5590d"));
        user_cnt++;
        redressor_ids.add(UUID.fromString("617ac0ad-99e6-4807-9a43-c4c1aab5590d"));
        users.get(user_cnt).setUser_id(UUID.fromString("8715a2e3-aaad-4a87-a006-370271813954"));
        user_cnt++;
        redressor_ids.add(UUID.fromString("8715a2e3-aaad-4a87-a006-370271813954"));
    }
    public void createRandomEndUsers(){
        endUser_ids = new ArrayList<>();
        for(int i=0;i<5;i++){
            users.add(createRandomUser());
        }
        users.get(user_cnt).setUser_id(UUID.fromString("a90597af-6867-457e-b7c5-a2e72b0c9dd6"));
        user_cnt++;
        endUser_ids.add(UUID.fromString("a90597af-6867-457e-b7c5-a2e72b0c9dd6"));
        users.get(user_cnt).setUser_id(UUID.fromString("ddcf2b69-e14e-4185-b00f-2d8ac465d4bd"));
        user_cnt++;
        endUser_ids.add(UUID.fromString("ddcf2b69-e14e-4185-b00f-2d8ac465d4bd"));
        users.get(user_cnt).setUser_id(UUID.fromString("be6318d5-4035-4a2f-97ea-231cf1db71eb"));
        user_cnt++;
        endUser_ids.add(UUID.fromString("be6318d5-4035-4a2f-97ea-231cf1db71eb"));
        users.get(user_cnt).setUser_id(UUID.fromString("54117b13-c044-4ffc-a97d-26b7a9431383"));
        user_cnt++;
        endUser_ids.add(UUID.fromString("54117b13-c044-4ffc-a97d-26b7a9431383"));
        users.get(user_cnt).setUser_id(UUID.fromString("dcf2e361-2ab0-4aa7-b743-f8db62a2100b"));
        user_cnt++;
        endUser_ids.add(UUID.fromString("dcf2e361-2ab0-4aa7-b743-f8db62a2100b"));
    }
    @Override
    public boolean validateDepartment(UUID user_id, UUID department_id) {
        for(User user:users){
            if(user.getUser_id().toString().equals(user_id.toString()) && user.getDepartment().getDepartment_id().toString().equals(department_id.toString())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean validateRegion(UUID user_id, UUID region_id) {
        for(User user:users){
            if(user.getUser_id().equals(user_id) && user.getRegion().getRegion_id().equals(region_id)){
                return true;
            }
        }
        return false;
    }

    public User createRandomUser() {
        mockDepartmentService.createRandomDepartments();
        mockRegionService.createRandomRegions();
        mockRoleService.createRandomRoles();
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
    public User createRandomAdmin(){
        mockDepartmentService.createRandomDepartments();
        mockRegionService.createRandomRegions();
        mockRoleService.createRandomRoles();
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
    public User createRandomRedressor(){
        mockDepartmentService.createRandomDepartments();
        mockRegionService.createRandomRegions();
        mockRoleService.createRandomRoles();
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
    public User getRandomRedressor(){
        Random random = new Random();
        UUID id = redressor_ids.get(random.nextInt(redressor_ids.size()));
        for(User user : users){
            if(user.getUser_id().equals(id)) return user;
        }
        return users.get(0); // if not found return first user
    }
    public User getRandomAdmin(){
        Random random = new Random();
        UUID id = admin_ids.get(random.nextInt(admin_ids.size()));
        for(User user : users){
            if(user.getUser_id().equals(id)) return user;
        }
        return users.get(0); // if not found return first user
    }
    public User getRandomEndUser(){
        Random random = new Random();
        UUID id = endUser_ids.get(random.nextInt(endUser_ids.size()));
        for(User user : users){
            if(user.getUser_id().equals(id)) return user;
        }
        return users.get(0); // if not found return first user
    }
}
