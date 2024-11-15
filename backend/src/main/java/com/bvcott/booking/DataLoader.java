package com.bvcott.booking;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bvcott.booking.config.GlobalFeeConfig;
import com.bvcott.booking.model.address.Address;
import com.bvcott.booking.model.hotel.Facility;
import com.bvcott.booking.model.hotel.Hotel;
import com.bvcott.booking.model.hotel.HotelRoom;
import com.bvcott.booking.model.hotel.HotelRoomType;
import com.bvcott.booking.model.user.Administrator;
import com.bvcott.booking.model.user.HotelOwner;
import com.bvcott.booking.repository.UserRepository;
import com.bvcott.booking.repository.config.GlobalFeeConfigRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepo;
    private final GlobalFeeConfigRepository feeRepo;

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.count() == 0 && feeRepo.count() == 0) {
            prepopulateData();
        }
    }

    private void prepopulateData() {
        // Create Administrators
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

        // Create HotelOwners
        HotelOwner owner1 = new HotelOwner();
        owner1.setUsername("hotelOwner1");
        owner1.setPassword("ownerPass1");
        owner1.setBalance(1500.0);

        HotelOwner owner2 = new HotelOwner();
        owner2.setUsername("hotelOwner2");
        owner2.setPassword("ownerPass2");
        owner2.setBalance(2000.0);

        HotelOwner owner3 = new HotelOwner();
        owner3.setUsername("hotelOwner3");
        owner3.setPassword("ownerPass3");
        owner3.setBalance(2500.0);

        HotelOwner owner4 = new HotelOwner();
        owner4.setUsername("hotelOwner4");
        owner4.setPassword("ownerPass4");
        owner4.setBalance(3000.0);

        // Create Global Fee Config
        GlobalFeeConfig fee = new GlobalFeeConfig();
        fee.setBaseCharge(100);
        fee.setChargePerRoom(10);
        fee.setTransactionFee(5);
        feeRepo.save(fee);

        // Create Hotels
        List<Hotel> hotels = List.of(
            createHotel("Ocean Breeze Resort", "Relax by the sea with stunning ocean views", "101", "Beachside", "90210", "Miami", "USA",
                    List.of(Facility.WIFI, Facility.POOL, Facility.AIR_CONDITIONING), 4.5),
            createHotel("Mountain Retreat Lodge", "Escape to the tranquility of the mountains", "55", "Alpine Way", "22345", "Aspen", "USA",
                    List.of(Facility.WIFI, Facility.GYM, Facility.SPA), 4.7),
            createHotel("Urban Paradise Hotel", "Luxurious stay in the heart of the city", "200", "Main Street", "10001", "New York", "USA",
                    List.of(Facility.BUSINESS_CENTER, Facility.RESTAURANT, Facility.BAR), 4.0),
            createHotel("Countryside Inn", "Cozy inn surrounded by beautiful countryside", "75", "Maple Drive", "33333", "Nashville", "USA",
                    List.of(Facility.BREAKFAST_INCLUDED, Facility.NON_SMOKING_ROOMS), 4.3),
            createHotel("Sunset Vista Hotel", "Experience breathtaking sunsets every evening", "34", "Sunset Blvd", "45678", "Los Angeles", "USA",
                    List.of(Facility.AIRPORT_SHUTTLE, Facility.POOL, Facility.TV), 3.9),
            createHotel("Royal Castle Suites", "A royal experience with luxurious rooms", "12", "Palace Road", "12345", "Edinburgh", "UK",
                    List.of(Facility.SPA, Facility.GYM, Facility.ROOM_SERVICE), 4.8),
            createHotel("Seaside Escape Hotel", "Comfortable and relaxing beachfront property", "88", "Ocean Drive", "54321", "Sydney", "Australia",
                    List.of(Facility.POOL, Facility.WIFI, Facility.KITCHEN), 4.2),
            createHotel("Central Business Hotel", "Conveniently located for business travelers", "300", "Downtown Ave", "67890", "Tokyo", "Japan",
                    List.of(Facility.BUSINESS_CENTER, Facility.WIFI, Facility.NON_SMOKING_ROOMS), 4.6),
            createHotel("Desert Oasis Hotel", "Luxury in the heart of the desert", "5", "Oasis Lane", "11223", "Dubai", "UAE",
                    List.of(Facility.SPA, Facility.AIR_CONDITIONING, Facility.MINI_BAR), 4.7),
            createHotel("Rainforest Resort", "Stay surrounded by lush rainforest", "67", "Rainforest Path", "66778", "Manaus", "Brazil",
                    List.of(Facility.POOL, Facility.WIFI, Facility.CHILDREN_FACILITIES), 4.3),
            createHotel("Historic Haven", "Experience history in this unique property", "1", "Old Town", "90001", "Rome", "Italy",
                    List.of(Facility.RESTAURANT, Facility.NON_SMOKING_ROOMS, Facility.WIFI), 4.0),
            createHotel("Arctic Chill Hotel", "A cool experience near the Arctic Circle", "10", "Iceberg Lane", "88888", "Reykjavik", "Iceland",
                    List.of(Facility.AIR_CONDITIONING, Facility.TV, Facility.LAUNDRY), 3.8),
            createHotel("Safari Adventure Lodge", "Explore the wild while staying in comfort", "99", "Savanna Road", "77123", "Nairobi", "Kenya",
                    List.of(Facility.AIR_CONDITIONING, Facility.KITCHEN, Facility.BAR), 4.4),
            createHotel("Island Paradise Hotel", "Exclusive private island stay", "101", "Paradise Cove", "56789", "Bali", "Indonesia",
                    List.of(Facility.SPA, Facility.WIFI, Facility.POOL), 4.9),
            createHotel("Skyline View Hotel", "Unmatched cityscape views from every room", "200", "Skyscraper Blvd", "55555", "Hong Kong", "China",
                    List.of(Facility.AIR_CONDITIONING, Facility.BAR, Facility.TV), 4.1)
        );

        // Assign rooms to hotels
        for (Hotel hotel : hotels) {
            hotel.setRooms(createHotelRooms());
        }

        // Assign hotels to owners
        owner1.getHotels().addAll(hotels.subList(0, 4)); // First 4 hotels
        owner2.getHotels().addAll(hotels.subList(4, 8)); // Next 4 hotels
        owner3.getHotels().addAll(hotels.subList(8, 12)); // Next 4 hotels
        owner4.getHotels().addAll(hotels.subList(12, 15)); // Last 3 hotels

        // Save HotelOwners (with cascading to hotels and rooms)
        userRepo.saveAll(List.of(admin1, admin2, admin3, admin4, owner1, owner2, owner3, owner4));
    }

    private Hotel createHotel(String name, String description, String streetNumber1, String streetNumber2, String postCode,
                              String city, String country, List<Facility> facilities, double rating) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setDescription(description);
        hotel.setRating(rating);

        Address address = new Address();
        address.setStreetNumber1(streetNumber1);
        address.setStreetNumber2(streetNumber2);
        address.setPostCode(postCode);
        address.setCity(city);
        address.setCountry(country);

        hotel.setAddress(address);
        hotel.setFacilities(facilities);

        return hotel;
    }

    private List<HotelRoom> createHotelRooms() {
        return List.of(
            createHotelRoom(HotelRoomType.SINGLE, 100.0, true),
            createHotelRoom(HotelRoomType.DOUBLE, 150.0, true),
            createHotelRoom(HotelRoomType.SUITE, 300.0, false),
            createHotelRoom(HotelRoomType.FAMILY, 250.0, true),
            createHotelRoom(HotelRoomType.DELUXE, 200.0, false)
        );
    }

    private HotelRoom createHotelRoom(HotelRoomType roomType, double price, boolean isAvailable) {
        HotelRoom room = new HotelRoom();
        room.setRoomType(roomType);
        room.setPrice(price);
        room.setAvailable(isAvailable);
        room.setCheckin(isAvailable ? null : LocalDate.now().minusDays(5)); // Example: Room checked in 5 days ago if unavailable
        room.setCheckout(isAvailable ? null : LocalDate.now().plusDays(3)); // Example: Room will be checked out in 3 days if unavailable
        return room;
    }
}