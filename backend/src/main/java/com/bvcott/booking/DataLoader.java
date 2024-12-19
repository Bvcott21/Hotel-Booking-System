package com.bvcott.booking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.bvcott.booking.model.payment.CardDetails;
import com.bvcott.booking.model.payment.PaymentDetails;
import com.bvcott.booking.model.user.Customer;
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

        // Create customers
        List<Customer> customers = createCustomers();

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
        userRepo.saveAll(customers);

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
        PaymentDetails paymentDetails1 = new PaymentDetails();
        PaymentDetails paymentDetails2 = new PaymentDetails();
        PaymentDetails paymentDetails3 = new PaymentDetails();
        PaymentDetails paymentDetails4 = new PaymentDetails();
        PaymentDetails paymentDetails5 = new PaymentDetails();
        PaymentDetails paymentDetails6 = new PaymentDetails();

        HotelOwner hotelOwner1 = new HotelOwner("hotelOwner1", "ownerPass1", 1500.0, List.of(paymentDetails1, paymentDetails2));
        HotelOwner hotelOwner2 = new HotelOwner("hotelOwner2", "ownerPass2", 2000.0, List.of(paymentDetails3, paymentDetails4));
        HotelOwner hotelOwner3 = new HotelOwner("hotelOwner3", "ownerPass3", 2500.0, List.of(paymentDetails5));
        HotelOwner hotelOwner4 = new HotelOwner("hotelOwner4", "ownerPass4", 3000.0, List.of(paymentDetails6));

        Address address1 = new Address("streetno1-hotelowner1-bankaddress1", "streetno2-hotelowner1-bankaddress1", "postcode1-hotelowner1-bankaddress1", "Miami", "USA");
        Address address2 = new Address("streetno1-hotelowner1-bankaddress2", "streetno2-hotelowner1-bankaddress2", "postcode2-hotelowner1-bankaddress2", "Caracas", "Venezuela");
        Address address3 = new Address("streetno1-hotelowner2-bankaddress1", "streetno2-hotelowner2-bankaddress1", "postcode1-hotelowner2-bankaddress1", "London", "United Kingdom");
        Address address4 = new Address("streetno1-hotelowner2-bankaddress2", "streetno2-hotelowner2-bankaddress2", "postcode2-hotelowner2-bankaddress2", "California", "USA");
        Address address5 = new Address("streetno1-hotelowner3-bankaddress1", "streetno2-hotelowner3-bankaddress1", "postcode1-hotelowner3-bankaddress1", "Coro", "Venezuela");
        Address address6 = new Address("streetno1-hotelowner4-bankaddress1", "streetno2-hotelowner4-bankaddress1", "postcode1-hotelowner4-bankaddress1", "Madrid", "Spain");

        CardDetails cardDetails1 = new CardDetails("1123432342345434", "423", LocalDate.of(2020, 11, 1), LocalDate.of(2028, 11, 1), address1);
        CardDetails cardDetails2 = new CardDetails("1236785678987655", "000", LocalDate.of(2019, 7, 1), LocalDate.of(2027, 7, 1), address2);
        CardDetails cardDetails3 = new CardDetails("3344567389786567", "312", LocalDate.of(2015, 12, 1), LocalDate.of(2025, 12, 1), address3);
        CardDetails cardDetails4 = new CardDetails("3554687915467454", "908", LocalDate.of(2024, 12, 1), LocalDate.of(2034, 12, 1), address4);
        CardDetails cardDetails5 = new CardDetails("3312454564567547", "770", LocalDate.of(2025, 1, 1), LocalDate.of(2035, 1, 1), address5);
        CardDetails cardDetails6 = new CardDetails("4323456547556475", "112", LocalDate.of(2021, 6, 1), LocalDate.of(2031, 6, 1), address6);

        paymentDetails1.setEndUser(hotelOwner1);
        paymentDetails1.setCardDetails(cardDetails1);
        paymentDetails2.setEndUser(hotelOwner1);
        paymentDetails2.setCardDetails(cardDetails2);
        paymentDetails3.setEndUser(hotelOwner2);
        paymentDetails3.setCardDetails(cardDetails3);
        paymentDetails4.setEndUser(hotelOwner2);
        paymentDetails4.setCardDetails(cardDetails4);
        paymentDetails5.setEndUser(hotelOwner3);
        paymentDetails5.setCardDetails(cardDetails5);
        paymentDetails6.setEndUser(hotelOwner4);
        paymentDetails6.setCardDetails(cardDetails6);

        return List.of(hotelOwner1, hotelOwner2, hotelOwner3, hotelOwner4);
    }

    private List<Customer> createCustomers() {
        PaymentDetails paymentDetails1 = new PaymentDetails();
        PaymentDetails paymentDetails2 = new PaymentDetails();
        PaymentDetails paymentDetails3 = new PaymentDetails();
        PaymentDetails paymentDetails4 = new PaymentDetails();
        PaymentDetails paymentDetails5 = new PaymentDetails();
        PaymentDetails paymentDetails6 = new PaymentDetails();

        Customer customer1 = new Customer("customer1", "customerPass1", List.of(paymentDetails1, paymentDetails2));
        Customer customer2 = new Customer("customer2", "customerPass2", List.of(paymentDetails3, paymentDetails4));
        Customer customer3 = new Customer("customer3", "customerPass3", List.of(paymentDetails5));
        Customer customer4 = new Customer("customer4", "customerPass4", List.of(paymentDetails6));

        Address address1 = new Address("streetno1-customer1-bankaddress1", "streetno2-customer1-bankaddress1", "postcode1-customer1-bankaddress1", "Miami", "USA");
        Address address2 = new Address("streetno1-customer1-bankaddress2", "streetno2-customer1-bankaddress2", "postcode2-customer1-bankaddress2", "Caracas", "Venezuela");
        Address address3 = new Address("streetno1-customer2-bankaddress1", "streetno2-customer2-bankaddress1", "postcode1-customer2-bankaddress1", "London", "United Kingdom");
        Address address4 = new Address("streetno1-customer2-bankaddress2", "streetno2-customer2-bankaddress2", "postcode2-customer2-bankaddress2", "California", "USA");
        Address address5 = new Address("streetno1-customer3-bankaddress1", "streetno2-customer3-bankaddress1", "postcode1-customer3-bankaddress1", "Coro", "Venezuela");
        Address address6 = new Address("streetno1-customer4-bankaddress1", "streetno2-customer4-bankaddress1", "postcode1-customer4-bankaddress1", "Madrid", "Spain");

        CardDetails cardDetails1 = new CardDetails("1123488982345434", "423", LocalDate.of(2020, 11, 1), LocalDate.of(2028, 11, 1), address1);
        CardDetails cardDetails2 = new CardDetails("1236736548987655", "000", LocalDate.of(2019, 7, 1), LocalDate.of(2027, 7, 1), address2);
        CardDetails cardDetails3 = new CardDetails("3344511189786567", "312", LocalDate.of(2015, 12, 1), LocalDate.of(2025, 12, 1), address3);
        CardDetails cardDetails4 = new CardDetails("3554088985467454", "908", LocalDate.of(2024, 12, 1), LocalDate.of(2034, 12, 1), address4);
        CardDetails cardDetails5 = new CardDetails("3312111222567547", "770", LocalDate.of(2025, 1, 1), LocalDate.of(2035, 1, 1), address5);
        CardDetails cardDetails6 = new CardDetails("4323665547556475", "112", LocalDate.of(2021, 6, 1), LocalDate.of(2031, 6, 1), address6);

        paymentDetails1.setEndUser(customer1);
        paymentDetails1.setCardDetails(cardDetails1);
        paymentDetails2.setEndUser(customer1);
        paymentDetails2.setCardDetails(cardDetails2);
        paymentDetails3.setEndUser(customer2);
        paymentDetails3.setCardDetails(cardDetails3);
        paymentDetails4.setEndUser(customer2);
        paymentDetails4.setCardDetails(cardDetails4);
        paymentDetails5.setEndUser(customer3);
        paymentDetails5.setCardDetails(cardDetails5);
        paymentDetails6.setEndUser(customer4);
        paymentDetails6.setCardDetails(cardDetails6);

        return List.of(customer1, customer2, customer3, customer4);
    }

    private List<Hotel> createHotels() {
        return List.of(
            createHotel("Ocean Breeze Resort", "Relax by the sea with stunning ocean views", "101", "Beachside", "90210", "Miami", "USA",
                    List.of(Facility.WIFI, Facility.POOL, Facility.AIR_CONDITIONING), 4.5, 0),
            createHotel("Mountain Retreat Lodge", "Escape to the tranquility of the mountains", "55", "Alpine Way", "22345", "Aspen", "USA",
                    List.of(Facility.WIFI, Facility.GYM, Facility.SPA), 4.7, 0),
            createHotel("Urban Paradise Hotel", "Luxurious stay in the heart of the city", "200", "Main Street", "10001", "New York", "USA",
                    List.of(Facility.BUSINESS_CENTER, Facility.RESTAURANT, Facility.BAR), 4.0, 4.3),
            createHotel("Countryside Inn", "Cozy inn surrounded by beautiful countryside", "75", "Maple Drive", "33333", "Nashville", "USA",
                    List.of(Facility.BREAKFAST_INCLUDED, Facility.NON_SMOKING_ROOMS), 4.3, 9.0)
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
                              String city, String country, List<Facility> facilities, double rating, double discount) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setDescription(description);
        hotel.setRating(rating);
        hotel.setDiscount(discount);

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