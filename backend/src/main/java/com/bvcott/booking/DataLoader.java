package com.bvcott.booking;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bvcott.booking.model.Administrator;
import com.bvcott.booking.model.HotelOwner;
import com.bvcott.booking.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepo;

    public DataLoader(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepo.count() == 0) {
            prepopulateData();
        }
    }

    private void prepopulateData() {
        // Create 4 Administrator instances
        Administrator admin1 = new Administrator();
        admin1.setUsername("admin1");
        admin1.setPassword("adminPass1");

        Administrator admin2 = new Administrator();
        admin2.setUsername("admin2");
        admin2.setPassword("adminPass2");

        Administrator admin3 = new Administrator();
        admin3.setUsername("admin3");
        admin3.setPassword("adminPass3");

        Administrator admin4 = new Administrator();
        admin4.setUsername("admin4");
        admin4.setPassword("adminPass4");

        // Create 4 HotelOwner instances
        HotelOwner owner1 = new HotelOwner();
        owner1.setUsername("hotelOwner1");
        owner1.setPassword("ownerPass1");

        HotelOwner owner2 = new HotelOwner();
        owner2.setUsername("hotelOwner2");
        owner2.setPassword("ownerPass2");

        HotelOwner owner3 = new HotelOwner();
        owner3.setUsername("hotelOwner3");
        owner3.setPassword("ownerPass3");

        HotelOwner owner4 = new HotelOwner();
        owner4.setUsername("hotelOwner4");
        owner4.setPassword("ownerPass4");

        userRepo.saveAll(List.of(admin1, admin2, admin3, admin4, owner1, owner2, owner3, owner4));
    }
}
