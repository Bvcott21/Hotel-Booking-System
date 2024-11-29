package com.bvcott.booking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bvcott.booking.config.GlobalFeeConfig;
import com.bvcott.booking.model.address.Address;
import com.bvcott.booking.model.booking.Booking;
import com.bvcott.booking.model.hotel.Facility;
import com.bvcott.booking.model.hotel.Hotel;
import com.bvcott.booking.model.hotel.HotelRoom;
import com.bvcott.booking.model.hotel.HotelRoomType;
import com.bvcott.booking.model.user.Administrator;
import com.bvcott.booking.model.user.HotelOwner;
import com.bvcott.booking.repository.UserRepository;
import com.bvcott.booking.repository.booking.BookingRepository;
import com.bvcott.booking.repository.config.GlobalFeeConfigRepository;
import com.bvcott.booking.repository.hotel.HotelRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepo;
    private final HotelRepository hotelRepo;
    private final BookingRepository bookingRepo;
    private final GlobalFeeConfigRepository feeRepo;

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.count() == 0 && feeRepo.count() == 0) {
            prepopulateData();
        }
    }

    private void prepopulateData() {
        // Create Administrators
        List<Administrator> admins = createAdministrators();

        // Create Hotel Owners
        List<HotelOwner> hotelOwners = createHotelOwners();

        // Create Global Fee Config
        GlobalFeeConfig fee = new GlobalFeeConfig();
        fee.setBaseCharge(100);
        fee.setChargePerRoom(10);
        fee.setTransactionFee(5);
        feeRepo.save(fee);

        // Create Hotels
        List<Hotel> hotels = createHotels();

        // Assign rooms to hotels
        hotels.forEach(hotel -> hotel.setRooms(createHotelRooms()));

        // Assign hotels to owners
        assignHotelsToOwners(hotels, hotelOwners);

        // Save HotelOwners (with cascading to hotels and rooms)
        userRepo.saveAll(admins);
        userRepo.saveAll(hotelOwners);

        // Create Bookings
        List<Booking> bookings = createBookingsForHotels(hotels);

        // Save bookings
        bookingRepo.saveAll(bookings);
        hotelRepo.saveAll(hotels); // Persist updated hotels and rooms
    }

    private List<Administrator> createAdministrators() {
        return List.of(
            new Administrator("admin1", "adminPass1"),
            new Administrator("admin2", "adminPass2"),
            new Administrator("admin3", "adminPass3"),
            new Administrator("admin4", "adminPass4")
        );
    }

    private List<HotelOwner> createHotelOwners() {
        return List.of(
            new HotelOwner("hotelOwner1", "ownerPass1", 1500.0),
            new HotelOwner("hotelOwner2", "ownerPass2", 2000.0),
            new HotelOwner("hotelOwner3", "ownerPass3", 2500.0),
            new HotelOwner("hotelOwner4", "ownerPass4", 3000.0)
        );
    }

    private List<Hotel> createHotels() {
        return List.of(
            createHotel("Ocean Breeze Resort", "Relax by the sea with stunning ocean views", "101", "Beachside", "90210", "Miami", "USA",
                    List.of(Facility.WIFI, Facility.POOL, Facility.AIR_CONDITIONING), 4.5),
            createHotel("Mountain Retreat Lodge", "Escape to the tranquility of the mountains", "55", "Alpine Way", "22345", "Aspen", "USA",
                    List.of(Facility.WIFI, Facility.GYM, Facility.SPA), 4.7),
            createHotel("Urban Paradise Hotel", "Luxurious stay in the heart of the city", "200", "Main Street", "10001", "New York", "USA",
                    List.of(Facility.BUSINESS_CENTER, Facility.RESTAURANT, Facility.BAR), 4.0),
            createHotel("Countryside Inn", "Cozy inn surrounded by beautiful countryside", "75", "Maple Drive", "33333", "Nashville", "USA",
                    List.of(Facility.BREAKFAST_INCLUDED, Facility.NON_SMOKING_ROOMS), 4.3)
        );
    }

    private void assignHotelsToOwners(List<Hotel> hotels, List<HotelOwner> owners) {
        owners.get(0).getHotels().addAll(hotels.subList(0, 2));
        owners.get(1).getHotels().addAll(hotels.subList(2, 4));
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
        room.setCheckin(isAvailable ? null : LocalDate.now().minusDays(5));
        room.setCheckout(isAvailable ? null : LocalDate.now().plusDays(3));
        return room;
    }

    private List<Booking> createBookingsForHotels(List<Hotel> hotels) {
        List<Booking> bookings = new ArrayList<>();

        for (Hotel hotel : hotels) {
            List<HotelRoom> availableRooms = hotel.getRooms().stream()
                .filter(HotelRoom::isAvailable)
                .limit(2) // Only 2 rooms per hotel are booked
                .toList();

            if (!availableRooms.isEmpty()) {
                Booking booking = new Booking();
                booking.setHotel(hotel);
                booking.setRooms(availableRooms);
                booking.setCheckin(LocalDate.now().plusDays(2));
                booking.setCheckout(LocalDate.now().plusDays(5));
                booking.setPrice(availableRooms.stream().mapToDouble(HotelRoom::getPrice).sum());

                bookings.add(booking);

                // Update room availability
                availableRooms.forEach(room -> {
                    room.setAvailable(false);
                    room.setCheckin(booking.getCheckin());
                    room.setCheckout(booking.getCheckout());
                });
            }
        }
        return bookings;
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
}