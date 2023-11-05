package com.grievanceredressalsystem.analyticsservice.mock.services;

import com.grievanceredressalsystem.analyticsservice.mock.models.Region;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service("mockRegionService")
public class MockRegionService {
    private static List<Region> regions;
    public void createRandomRegions(){
        regions = new ArrayList<>();
//        for(int i=0;i<n;i++)
//            regions.add(new Region(UUID.randomUUID(),Faker.instance().address().cityName()));
        regions.add(new Region(UUID.fromString("c8bdbb5f-689d-42de-996b-fbd31990fbf9"),"North Merrilltown"));
        regions.add(new Region(UUID.fromString("33d2e874-5e08-41e2-b535-cf75748a2da7"),"Port Julian"));
        regions.add(new Region(UUID.fromString("602df2f8-6450-4f1a-aa70-61283a8792de"),"Wilbershire"));
        regions.add(new Region(UUID.fromString("382e4cdf-7e39-4635-960c-cdb03408ea63"),"North Octaviotown"));
        regions.add(new Region(UUID.fromString("f633bfea-bb6b-4a0e-b3f6-938681bc3d79"),"Koelpinbury"));
    }
    public Region getRandomRegion(){
        Random random = new Random();
        return regions.get(random.nextInt(regions.size()));
    }
}
